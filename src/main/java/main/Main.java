package main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.*;
import entities.*;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import spark.Session;
import util.Filters;
import util.Path;
import util.Rest.JSONUtil;
import util.Rest.ResService;
import util.Soap.SoapMain;
import util.ViewUtil;
import util.BootStrapServices;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    // Declare dependencies
    private static UserDAO userDAO;
    private static PostDAO postDAO;
    private static FriendRequestDAO friendRequestDAO;
    private static NotificationDAO notificationDAO;
    private static ImageDAO imageDAO;

    private final static String ACCEPT_TYPE_JSON = "application/json";


    public static void main(String[] args) {
        // Launch Database
        BootStrapServices.getInstance().init();

        port(getHerokuAsignatedPort());

        //Instantiate dependencies
        userDAO = new UserDAO(User.class);
        postDAO = new PostDAO(Post.class);
        friendRequestDAO = new FriendRequestDAO(FriendRequest.class);
        notificationDAO = new NotificationDAO(Notification.class);
        imageDAO = new ImageDAO(Image.class);

        // Launch SOAP Services
        try{
            SoapMain.init();
        }catch (Exception e) {
            System.out.println("No se pudeo inicializar el servicio por: ");
            e.printStackTrace();
        }

        // Launch REST Services
        ResService resService = new ResService();

        // Configure Spark
        staticFiles.location("/public");

        File uploadDir = new File("UploadedImages");

        if (uploadDir.mkdir()) {
            System.out.println("The **Uploaded Images** directory was created");
        }

        staticFiles.externalLocation("UploadedImages");


        // Creating default user if there are none
        if (userDAO.isEmpty()) {
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
            User user = new User("admin", "Demon King", "Meliodas",
                    new Date(),
                    encryptor.encryptPassword("admin123"),
                    true,
                    "Santiago de los Caballeros");
            userDAO.persist(user);
        }

        // Route filters
        Filters.filters();

        // Handling errors
        notFound(ViewUtil.notFound);
        internalServerError(ViewUtil.internalServerError);

        // Register routes
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            User user = request.session().attribute("currentUser");
            model.put("suggestedFriendList", userDAO.getSuggestedFriends(user));
            return ViewUtil.render(request, model, Path.INDEX) ;
        });

        get("/wall", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
//            int page = Integer.parseInt(request.queryParams("page"));
            int page = 0;

            List<Notification> notificationList = notificationDAO.findAll();
            List<Post> postList = postDAO.findSomeByUser( page * 10 , request.session().attribute("currentUser"));
            model.put("postList", postList);
            model.put("user", request.session().attribute("currentUser"));
            model.put("notificationList", notificationList);
            return ViewUtil.render(request, model, Path.WALL);
        });

        get("/walls/:user", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            //            int page = Integer.parseInt(request.queryParams("page"));
            int page = 0;
            User user = userDAO.find(request.params("user"));
            List<Post> postList = postDAO.findSomeByUser( page * 10 , user);
            model.put("postList", postList);
            model.put("user", user);
            return ViewUtil.render(request, model, Path.WALL);
        });

        post("/post", (request, response) -> {
            Post post = new Post();
            post.setContent(request.queryParams("post-content"));
            post.setDate(new Date());
            post.setUser(request.session().attribute("currentUser"));

            postDAO.persist(post);


            java.nio.file.Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream input = request.raw().getPart("unploadImage").getInputStream()) {
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            //logInfo(request, tempFile);
            Image image = new Image();
            image.setPath(uploadDir.toPath() + "/" + tempFile.getFileName());

            imageDAO.persist(image);

            response.redirect("/wall");

            return null;
        });

        get("/register", (request, response) -> ViewUtil.render(request, new HashMap<>(), Path.REGISTER));

        post("/register",(request,response)->{
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
            User user = new User();

            user.setUsername(request.queryParams("username"));
            user.setPassword(encryptor.encryptPassword(request.queryParams("password")));
            user.setName(request.queryParams("firstName"));
            user.setLastname(request.queryParams("lastName"));
            user.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(request.queryParams("bornDate")));
            user.setAdministrator(false);
            user.setCity(request.queryParams("city"));
            userDAO.persist(user);

            request.session().attribute("currentUser", user);

            response.redirect("/");
            return null;
        });

        // Serve login
        get("/login", (request, response) ->{
            Map<String,String> cookies = request.cookies();
            Map<String, Object> model = new HashMap<>();

            if (request.cookies().containsKey("JSESSIONID")){
                for(String key: cookies.keySet()){
                    if(key.equalsIgnoreCase("cookie")){

                        String encryptedText = request.cookie("cookie");
                        BasicTextEncryptor encryptor = new BasicTextEncryptor();
                        encryptor.setPassword("secretPasswd");
                        String usern = encryptor.decrypt(encryptedText);
                        model.put("authenticationSucceeded",true);
                        request.session(true).attribute("currentUser", userDAO.find(usern));
                        response.redirect("/");
                    }
                }
            }

            return ViewUtil.render(request,model,Path.LOGIN);

        });

        // Handle login
        post("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            if(request.queryParams("remember-me") == null){
                if (authenticate(request.queryParams("username"), request.queryParams("password"))) {
                    model.put("authenticationFailed", true);
                    return ViewUtil.render(request, model, Path.LOGIN);
                }
                model.put("authenticationSucceeded", true);
                User user = userDAO.find(request.queryParams("username"));
                request.session(true).attribute("currentUser", user);

            }else if(request.queryParams("remember-me").equals("on")){
                if (authenticate(request.queryParams("username"), request.queryParams("password"))) {
                    model.put("authenticationFailed", true);
                    return ViewUtil.render(request, model, Path.LOGIN);
                }
                BasicTextEncryptor tempEncryptor = new BasicTextEncryptor();
                tempEncryptor.setPassword("secretPasswd");
                response.cookie("/","cookie",tempEncryptor.encrypt(request.queryParams("username")),604800, false);
                model.put("authenticationSucceeded", true);
                User user = userDAO.find(request.queryParams("username"));
                request.session(true).attribute("currentUser", user);
            }

            if (request.queryParams("loginRedirect") != null) {
                response.redirect(request.queryParams("loginRedirect"));
            }

            response.redirect("/");

            return null;
        });

        get("/logout", (request, response) -> {
            /*request.session().removeAttribute("currentUser");
            request.session().attribute("loggedOut", true);
            response.redirect(Path.LOGIN);
            return null;*/
            Session activeSession = request.session();
            activeSession.invalidate();
            response.removeCookie("cookie");
            response.redirect("/");

            return null;
        });

        path("/rest", ()->{
           afterAfter("/*", (request,response)->{
               if(request.headers("Accept").equalsIgnoreCase(ACCEPT_TYPE_JSON)){
                   response.header("Content-Type", ACCEPT_TYPE_JSON);
               }
           });

           path("/userPosts", ()->{
               get("/:username",(request,response)-> resService.getUserPosts(request.params("username")),JSONUtil.json());

               post("/createNewPost",ACCEPT_TYPE_JSON,(request,response)->{
                   String username ="";
                   String image = "";
                   String content = "";
                   if(request.headers("Content-Type").equalsIgnoreCase(ACCEPT_TYPE_JSON)){
//                       System.out.println(JSONUtil.toJson(request.body()));
                       JsonObject jsonObject = new Gson().fromJson(request.body(), JsonObject.class);
                       username += jsonObject.get("username");
                       content += jsonObject.get("content");
                       image += jsonObject.get("image");
                   }else {
                       throw new IllegalArgumentException("Este formato no es JSON");
                   }
                   return resService.createPost(content,image,username);
               },JSONUtil.json());
           });
        });

        get("/album", (request, response) -> ViewUtil.render(request, new HashMap<>(), Path.ALBUM));

        post("/album", (request, response) -> {

            response.redirect("/album");
            return null;
        });

        post("/friendRequest/:username", (request, response) -> {
            User targetUser = userDAO.find(request.params("username"));
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setRequestUser(request.session().attribute("currentUser"));
            friendRequest.setAccepted(false);
            friendRequest.setTargetUser(targetUser);
            friendRequestDAO.persist(friendRequest);

            Notification notification = new Notification();
            notification.setDescription(targetUser.getName() + " te ha mandado una solicitud de amistad");
            notification.setType(NotificationType.FRIEND_REQUEST);
            notification.setUser(targetUser);
            notification.setDate(new Date());
            notification.setSeen(false);

            notificationDAO.persist(notification);

            response.redirect("/walls/"+targetUser.getUsername());
            return null;
        });

    }

    // User Controller
    private static boolean authenticate(String username, String password) {
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        if (username.isEmpty() || password.isEmpty())
            return true;

        User user = userDAO.find(username);

        if (user == null) {
            return true;
        }

        return !encryptor.checkPassword(password, user.getPassword());
    }

    private static int getHerokuAsignatedPort(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

}

