package main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.*;
import entities.*;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import util.BootStrapServices;
import util.Filters;
import util.Path;
import util.Rest.JSONUtil;
import util.Rest.ResService;
import util.Soap.SoapMain;
import util.ViewUtil;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    // Declare dependencies
    public static UserDAO userDAO;
    private static PostDAO postDAO;
    public static FriendRequestDAO friendRequestDAO;
    public static NotificationDAO notificationDAO;
    private static ImageDAO imageDAO;
    private static VoteDAO voteDAO;
    private static CommentDAO commentDAO;

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
        voteDAO = new VoteDAO(Vote.class);
        commentDAO = new CommentDAO(Comment.class);
        Gson gson = new Gson();

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
        File uploadDir = new File("UploadedImages");

        if (uploadDir.mkdir()) {
            System.out.println("The **Uploaded Images** directory was created");
        }

        staticFiles.externalLocation("UploadedImages");

        staticFiles.location("/public");

        enableDebugScreen();

        // Creating default user if there are none
        if (userDAO.isEmpty()) {
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

            Image profileImageAdmin = new Image("/images/meliodas.jpg");
            imageDAO.persist(profileImageAdmin);
            Image portraitImageAdmin = new Image("/images/holywar.png");
            imageDAO.persist(portraitImageAdmin);

            User user = new User("admin", "Demon King", "Meliodas",
                    new Date(),
                    encryptor.encryptPassword("admin123"),
                    true,
                    "Santiago de los Caballeros", profileImageAdmin);
            user.setPortraitImage(portraitImageAdmin);
            userDAO.persist(user);
        }

        // Route filters
        Filters.filters();

        // Handling errors
        notFound(ViewUtil.notFound);
        internalServerError(ViewUtil.internalServerError);

        // Auth routes
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            User currentUser = request.session().attribute("currentUser");
            List<Post> postFiltrados = new ArrayList<>();
            List<User> amigos = userDAO.getFriends(currentUser);

            for (Post post :
                    postDAO.findAll()) {
//                post.setCommentList(commentDAO.getByPost(post));
                if (amigos.contains(post.getUser()) || post.getUser().equals(currentUser)) {
                    postFiltrados.add(post);
                }
             }
            model.put("previousRoute", request.pathInfo());
            model.put("postList", postFiltrados);
            return ViewUtil.render(request, model, Path.INDEX) ;
        });

        get("/walls/:user", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            
            User currentUser = request.session().attribute("currentUser");
            User wallOwner = userDAO.find(request.params("user"));

            wallOwner.setFriendList(userDAO.getFriends(wallOwner));

            FriendRequest friendRequest = friendRequestDAO.getFriendRequest(currentUser, wallOwner);
            List<Post> postList = postDAO.findSomeByUserTagged(wallOwner);

//            for (Post post : postList) {
//                post.setCommentList(commentDAO.getByPost(post));
//            }

            model.put("friendRequest", friendRequest);
            model.put("postList", postList);
            model.put("wallOwner", wallOwner);
            model.put("previousRoute", request.pathInfo());

            return ViewUtil.render(request, model, Path.WALL);
        });

        post("/post", (request, response) -> {
            User user = request.session().attribute("currentUser");

            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            String username = request.queryParams("tag");
            User taggedUser = userDAO.find(username);

            if (taggedUser != null) {
                Notification tagNotification = new Notification();
                tagNotification.setDate(new Date());
                tagNotification.setDescription(user.getName() +" " + user.getLastname() + " te ha etiquetado en su " + "publicación. Revisa tú muro.");
                tagNotification.setType(NotificationType.TAGGED);
                tagNotification.setSeen(false);
                tagNotification.setSenderUser(user);
                tagNotification.setUser(taggedUser);
                notificationDAO.persist(tagNotification);
            }

            Post post = new Post();
            post.setContent(request.queryParams("postContent"));
            post.setDate(new Date());
            post.setUser(user);
            post.setTaggedUser(taggedUser);

            java.nio.file.Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            Part part = request.raw().getPart("uploadImage");
            if (!part.getSubmittedFileName().isEmpty()) {
                try (InputStream input = part.getInputStream()) {
                    Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
                }

                Image image = new Image();
                // image.setPath(uploadDir.toPath() + "/" + tempFile.getFileName());
                image.setPath("/" + tempFile.getFileName());
                imageDAO.persist(image);
                post.setImage(image);
            } else {
                post.setImage(null);
            }

            postDAO.persist(post);

            response.redirect(request.queryParams("redirectPath"));

            return null;
        });



        get("/register", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("bodyBackground", "bgImgRegister");
            return ViewUtil.render(request, model, Path.REGISTER);
        });

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
            Image perfil = new Image("/images/monkey-face.png");
            imageDAO.persist(perfil);
            Image portrait = new Image("/images/default-portrait.png");
            imageDAO.persist(portrait);
            user.setProfileImage(perfil);
            user.setPortraitImage(portrait);
            user.setEstudyPlace(null);
            user.setWorkPlace(null);

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

            model.put("loginRedirect", request.session().attribute("loginRedirect"));
            request.session().removeAttribute("loginRedirect");

            model.put("bodyBackground", "loginBgImg");

            return ViewUtil.render(request,model,Path.LOGIN);

        });

        // Handle login
        post("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            if (request.queryParams("remember-me") == null) {
                if (authenticate(request.queryParams("username"), request.queryParams("password"))) {
                    model.put("authenticationFailed", true);
                    return ViewUtil.render(request, model, Path.LOGIN);
                }

            } else if(request.queryParams("remember-me").equals("on")) {
                if (authenticate(request.queryParams("username"), request.queryParams("password"))) {
                    model.put("authenticationFailed", true);
                    return ViewUtil.render(request, model, Path.LOGIN);
                }

                BasicTextEncryptor tempEncryptor = new BasicTextEncryptor();
                tempEncryptor.setPassword("secretPasswd");
                response.cookie("/","cookie",tempEncryptor.encrypt(request.queryParams("username")),604800, false);

            }

            model.put("authenticationSucceeded", true);
            User user = userDAO.find(request.queryParams("username"));
            request.session(true).attribute("currentUser", user);

            if (request.queryParams("loginRedirect") != null) {
                response.redirect(request.queryParams("loginRedirect"));
                halt();
            }

            response.redirect("/");

            return null;
        });

        get("/logout", (request, response) -> {
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

        get("/album", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            User currentUser = request.session().attribute("currentUser");
            User wallOwner = userDAO.find(request.params("user"));

            wallOwner.setFriendList(userDAO.getFriends(wallOwner));

            FriendRequest friendRequest = friendRequestDAO.getFriendRequest(currentUser, wallOwner);
//            List<Post> postList = postDAO.findSomeByUserTagged(wallOwner);

            model.put("friendRequest", friendRequest);
//            model.put("postList", postList);
            model.put("wallOwner", wallOwner);
            model.put("previousRoute", request.pathInfo());

            return ViewUtil.render(request, new HashMap<>(), Path.ALBUM);
        });

        post("/album", (request, response) -> {

            response.redirect("/album");
            return null;
        });

        post("/friendRequest/accept/:request-user", (request, response) -> {
            //TODO - Esto funcionaría mejor por AJAX
            User requestUser = userDAO.find(request.params("request-user"));
            User currentUser = request.session().attribute("currentUser");
            FriendRequest friendRequest = friendRequestDAO.getFriendRequest(requestUser, currentUser);
            friendRequestDAO.acceptFriendRequest(friendRequest);
            userDAO.establishFriendship(currentUser, requestUser);
            response.redirect("/");
            return null;
        });

        post("/friendRequest/:target-user", (request, response) -> {
            User targetUser = userDAO.find(request.params("target-user"));
            User currentUser = request.session().attribute("currentUser");
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setRequestUser(currentUser);
            friendRequest.setAccepted(false);
            friendRequest.setTargetUser(targetUser);
            friendRequestDAO.persist(friendRequest);

//            Notification notification = new Notification();
//            notification.setDescription(currentUser.getName() + " " + currentUser.getLastname() + " te ha mandado una solicitud de amistad");
//            notification.setType(NotificationType.FRIEND_REQUEST);
//            notification.setSenderUser(currentUser);
//            notification.setUser(targetUser);
//            notification.setDate(new Date());
//            notification.setSeen(false);
//
//            notificationDAO.persist(notification);

            response.redirect("/walls/"+targetUser.getUsername());
            return null;
        });

//        get("/friendRequest/delete/:request-user", (request, response) -> {
//            User requestUser = userDAO.find(request.params("request-user"));
//            User targetUser = request.session().attribute("currentUser");
//            FriendRequest friendRequest = friendRequestDAO.getFriendRequest(requestUser, targetUser);
//            friendRequestDAO.remove(friendRequest);
//            return null;
//        });
//
        get("/descartNotification/:notification_id/:user", (request, response) -> {
            Notification notification = notificationDAO.find(Long.parseLong(request.params("notification_id")));
            notification.setSeen(true);
            notificationDAO.update(notification);
            response.redirect("/walls/"+request.params("user"));
            return null;
        });

        post("isUsernameAvailable", (request, response) -> {
            if (userDAO.find(request.queryParams("username")) != null) {
                return "El nombre de usuario ya esta en uso.";
            }
            return true;
        }, gson::toJson);

        /*get("/registeredUsers/list",(request,response)->{
                Map<String, Object> model = new HashMap<>();
                model.put("usersList", userDAO.findAll());

                return ViewUtil.render(request,model,Path.REGISTEREDUSERS);
        });

        post("/registeredUsers",(request,response)->{ ;
                *//*JsonObject jsonObject = new Gson().fromJson(request.body(), JsonObject.class);
                String tempbool = "";
                tempbool += jsonObject.get("administrador");*//*
                User user = userDAO.find(request.queryParams("username"));
                user.setEdad(Period.between(user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears());
//                user.setAdministrator(Boolean.parseBoolean(tempbool));
                user.setAdministrator(true);
                userDAO.update(user);
                response.redirect("/registeredUsers/list");
                return null;
            });*/
        get("/userlist",(request,response)->{
           Map<String, Object> model = new HashMap<>();
           model.put("usersList", userDAO.findAll());

           return ViewUtil.render(request,model,Path.REGISTEREDUSERS);
        });/*
        post("/userlist", (ICRoute)(request)->{
            User user = userDAO.find(request.queryParams("username"));
            user.setEdad(Period.between(user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears());
            user.setAdministrator(true);
            userDAO.update(user);
//            response.redirect("/userlist");
        });*/
        post("/userlist", (request,response)->{
           User user = userDAO.find(request.queryParams("username"));
            user.setEdad(Period.between(user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears());
            user.setAdministrator(true);
            userDAO.update(user);
            response.redirect("/userlist");
            return null;
        });
        /*afterAfter("/registeredUsers/*", (request,response)->{
            response.header("Content-Type","application/json");
        });*//*
        get("/registeredUsers/list",(request,response)->{
            Map<String, Object> model = new HashMap<>();
            List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
            model.put("usersList", userDAO.findAll());

            return ViewUtil.render(request,model,Path.REGISTEREDUSERS);
        });

        post("/registeredUsers",(request,response)->{ ;
            JsonObject jsonObject = new Gson().fromJson(request.body(), JsonObject.class);
            String tempbool = "";
            tempbool += jsonObject.get("administrador");
            User user = userDAO.find(request.queryParams("username"));
            user.setEdad(Period.between(user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears());
            user.setAdministrator(Boolean.parseBoolean(tempbool));
//            user.setAdministrator(true);
            userDAO.update(user);

            return null;
        });*/


        post("/post/vote", (req, res) -> {
            Long postid = Long.parseLong(req.queryParams("id"));
            String type = req.queryParams("type");

            User currentUser = req.session().attribute("currentUser");
            Post post = postDAO.find(postid);

            Vote vote = voteDAO.findByUserNPost(postid, currentUser.getUsername());

            if (vote != null) {
                vote.setType(type);
                voteDAO.update(vote);
            } else {
                vote = new Vote(type, currentUser, post);
                voteDAO.persist(vote);
            }

            String[] counts = new String[2];
            counts[0] = voteDAO.voteCountByPost(postid, "like");
            counts[1] = voteDAO.voteCountByPost(postid, "dislike");

            return counts[0] + "," + counts[1];
        });

        post("/comment", (request, response) -> {
            User user = request.session().attribute("currentUser");
            String content = request.queryParams("content");
            Post post = postDAO.find(Long.parseLong(request.queryParams("postId")));
            Date date = new Date();
            Comment comment = new Comment(content, user, post, date);
            commentDAO.persist(comment);

            return getCommentHTML(user, date, content, comment);
        });

        post("/comment/vote", (req, res) -> {
            String stringId = req.queryParams("id");
            Long commentid = Long.parseLong(stringId);
            String type = req.queryParams("type");

            User currentUser = req.session().attribute("currentUser");
            Comment comment = commentDAO.find(commentid);

            Vote vote = voteDAO.findByUserNComment(commentid, currentUser.getUsername());

            if (vote != null) {
                vote.setType(type);
                voteDAO.update(vote);
            } else {
                vote = new Vote(type, currentUser, comment);
                voteDAO.persist(vote);
            }

            String[] counts = new String[2];
            counts[0] = voteDAO.voteCountByComment(commentid, "like");
            counts[1] = voteDAO.voteCountByComment(commentid, "dislike");

            return counts[0] + "," + counts[1];
        });

    }

    public static String getCommentHTML(User user, Date date, String content, Comment comment) {
        return "<div class=\"comment row\">\n" +
                    "<div class=\"col-sm-2 px-0\">\n" +
                        "<img alt=\"PIC\" class=\"comment-pic\" src=\"" + user.getProfileImage().getPath() + "\">\n" +
                    "</div>\n" +

                    "<div class=\"col-sm-10\">\n" +
                        "<span><strong>" + user.getName() + " " + user.getLastname() + "</strong></span>\n" +
                        "<span class=\"text-muted mx-1\"><time datetime=\"" + date + "\">" + date + "</time></span>\n" +

                        "<div class=\"comment-content\">\n" +
                            content +
                        "</div>\n" +

                        "<div>\n" +
                            "<form class=\"ajax\" action=\"/comment/vote\" method=\"POST\" style=\"display: inline;\" data-id=\""+  comment.getId() +"\" data-type=\"like\">\n" +
                                "<button class=\"btn btn-link\">\uD83D\uDC4D Me gusta</button><span id=\"badge-like" + comment.getId() + "\" class=\"badge\">0</span>\n" +
                            "</form>\n" +
                            "<form class=\"ajax\" action=\"/comment/vote\" method=\"POST\" style=\"display: inline;\" data-id=\"" + comment.getId() + "\" data-type=\"dislike\">\n" +
                                "<button  class=\"btn btn-link\">\uD83D\uDC4E No me gusta</button><span id=\"badge-dislike"+comment.getId()+"\" class=\"badge\">0</span>\n" +
                            "</form>" +
                        "</div>"+
                    "</div>\n" +
                "</div>";
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

    @FunctionalInterface
    private interface ICRoute extends Route {
        default Object handle(Request request, Response response) throws Exception {
            handle(request);
            return "";
        }
        void handle(Request request) throws Exception;
    }

    private static int getHerokuAsignatedPort(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

}

