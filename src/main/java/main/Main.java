package main;

import com.google.gson.Gson;
import dao.DAOImpl;
import dao.PostDAO;
import dao.UserDAO;
import entities.City;
import entities.Post;
import entities.User;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import spark.Request;
import spark.Session;
import util.Filters;
import util.Path;
import util.Rest.JSONUtil;
import util.Rest.ResService;
import util.ViewUtil;
import util.BootStrapServices;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    // Declare dependencies
    public static UserDAO userDAO;
    public static PostDAO postDAO;
    public final static String ACCEPT_TYPE_JSON = "application/json";


    public static void main(String[] args) {
        // Launch Database
        BootStrapServices.getInstance().init();

        // Launch SOAPServices
//        try{
//            SoapMain.init();
//        }catch (Exception e) {
//            System.out.println("No se pudeo inicializar el servicio por: ");
//            e.printStackTrace();
//        }

        ResService resService = new ResService();
        // Configure Spark
        staticFiles.location("/public");

        File uploadDir = new File("parcial2UploadImages");
        uploadDir.mkdir();

        staticFiles.externalLocation("parcial2UploadImages");

        //Instantiate dependencies
        userDAO = new UserDAO(User.class);


        // Creating default user if there are none
        if (userDAO.findAll().isEmpty()) {
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
            User user = new User("admin", "Escanor", "Castilla",
                    new Date(), encryptor.encryptPassword("admin123"), true,
                    new City("Santiago de los Caballeros","Santiago","51000","República Dominicana", null,null));
            userDAO.persist(user);
        }
        postDAO = new PostDAO(Post.class);
        // Route filters
        Filters.filters();

        // Handling errors
        notFound(ViewUtil.notFound);
        internalServerError(ViewUtil.internalServerError);

        // Register routes
        get("/", (request, response) -> ViewUtil.render(request, new HashMap<>(), Path.INDEX));

        get("/wall", (request, response) -> {
//            int page = Integer.parseInt(request.queryParams("page"));
            int page = 0;
            List<Post> postList = postDAO.findSomeByUser( page * 10 , request.session().attribute("currentUser"));
            Map<String, Object> model = new HashMap<>();
            model.put("postList", postList);
            model.put("user", request.session().attribute("currentUser"));
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

            response.redirect("/wall");

            return null;
        });

        get("/register", (request, response) -> {
            return ViewUtil.render(request, new HashMap<>(), Path.REGISTER);
        });

        post("/register",(request,response)->{
            DAOImpl<City, String> cittyDao = new DAOImpl<>(City.class);
            City city = new City();
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
            User user = new User();
            user.setUsername(request.queryParams("username"));
            user.setPassword(encryptor.encryptPassword(request.queryParams("password")));
            user.setName(request.queryParams("firstName"));
            user.setLastname(request.queryParams("lastName"));
            user.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(request.queryParams("bornDate")));
            user.setAdministrator(false);
            if(cittyDao.findAll().isEmpty()){
                city.setName(request.queryParams("city"));
                city.setProvince(request.queryParams("city"));
                city.setPostalCode("51000");
                city.setCountry("República Dominicana");
                city.setEstudyPlaceList(null);
                city.setWorkPlaceList(null);
                cittyDao.persist(city);
            }
            user.setCityborn(city);
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
                        System.out.println(usern);
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
                if (!authenticate(request.queryParams("username"), request.queryParams("password"))) {
                    model.put("authenticationFailed", true);
                    return ViewUtil.render(request, model, Path.LOGIN);
                }
                model.put("authenticationSucceeded", true);
                User user = userDAO.find(request.queryParams("username"));
                request.session(true).attribute("currentUser", user);

            }else if(request.queryParams("remember-me").equals("on")){
                if (!authenticate(request.queryParams("username"), request.queryParams("password"))) {
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
               get("/:username",(request,response)->{
                  return resService.getUserPosts(request.params("username"));
               },JSONUtil.json());

               post("/createNewPost",ACCEPT_TYPE_JSON,(request,response)->{
                   Post post = null;
                   if(request.headers("Content-Type").equalsIgnoreCase(ACCEPT_TYPE_JSON)){
//                       System.out.println(JSONUtil.toJson(request.body()));
                        post = new Gson().fromJson(request.body(), Post.class);
                        System.out.println(post);
                   }else {
                       throw new IllegalArgumentException("Este formato no es JSON");
                   }
                   return resService.createPost(post);
               },JSONUtil.json());
           });
        });

        get("/album", (request, response) -> ViewUtil.render(request, new HashMap<>(), Path.ALBUM));

        post("/album", (request, response) -> {
            java.nio.file.Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            logInfo(request, tempFile);

            response.redirect("/album");
            return null;
        });

    }

    // User Controller
    public static boolean authenticate(String username, String password) {
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        if (username.isEmpty() || password.isEmpty())
            return false;

        User user = userDAO.find(username);

        if (user == null) {
            return false;
        }

        return encryptor.checkPassword(password, user.getPassword());
    }




    // methods used for logging With image unpload
    private static void logInfo(Request req, java.nio.file.Path tempFile) throws IOException, ServletException {
        System.out.println("Uploaded file '" + getFileName(req.raw().getPart("uploaded_file")) + "' saved as '" + tempFile.toAbsolutePath() + "'");
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}

