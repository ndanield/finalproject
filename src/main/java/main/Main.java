package main;

import com.google.gson.Gson;
import dao.DAO;
import dao.DAOImpl;
import dao.PostDAO;
import dao.UserDAO;
import entities.Post;
import entities.Post;
import entities.User;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;
import util.Filters;
import util.Path;
import util.Rest.JSONUtil;
import util.Rest.ResService;
import util.Soap.SoapMain;
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
        try{
            SoapMain.init();
        }catch (Exception e) {
            System.out.println("No se pudeo inicializar el servicio por: ");
            e.printStackTrace();
        }

        ResService resService = new ResService();
        // Configure Spark
        staticFiles.location("/public");

        File uploadDir = new File("parcial2UploadImages");
        uploadDir.mkdir();

        staticFiles.externalLocation("parcial2UploadImages");

        //Instantiate dependencies
        userDAO = new UserDAO(User.class);
        postDAO = new PostDAO(Post.class);


        // Creating default user if there are none
        if (userDAO.findAll().isEmpty()) {
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
            User user = new User("admin", "Escanor", "Castilla",
                    new Date(), encryptor.encryptPassword("admin123"), true);
            userDAO.persist(user);
        }

        // Route filters
        Filters.filters();

        // Handling errors
        notFound(ViewUtil.notFound);
        internalServerError(ViewUtil.internalServerError);

        // Register routes
        get("/", (request, response) -> ViewUtil.render(request, new HashMap<>(), Path.INDEX));

        get("/wall", (request, response) -> {
            int page = Integer.parseInt(request.queryParams("page"));
            List<Post> postList = postDAO.findSome( page * 10 );
            Map<String, Object> model = new HashMap<>();
            model.put("postList", postList);
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
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
            User user = new User();
            user.setUsername(request.queryParams("username"));
            user.setPassword(encryptor.encryptPassword(request.queryParams("password")));
            user.setName(request.queryParams("firstName"));
            user.setLastname(request.queryParams("lastName"));
            user.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(request.queryParams("bornDate")));
            user.setAdministrator(false);

            userDAO.persist(user);
            request.session(true).attribute("currentUser", user);
            response.redirect("/");
            return null;
        });

        // Serve login
        get("/login", (request, response) ->  {
            Map<Object, String> model = new HashMap<>();
//            String rememberMeCookie = request.cookie("remember-me");
//            if (rememberMeCookie != null) {
//                BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
//                textEncryptor.setPassword("secret");
//                // Elemento par[0] = username, par[1] = password
//                String[] par = textEncryptor.decrypt(rememberMeCookie).split(":");
//
//                if (!authenticate(par[0], par[1])) {
//
//                }
//
//                return ViewUtil.render(request, new HashMap<>(), )
//            }

            String loginRedirect = request.session().attribute("loginRedirect");
            request.session().removeAttribute("loginRedirect");
            model.put("loginRedirect", loginRedirect);

            return ViewUtil.render(request, new HashMap<>(), Path.LOGIN);
        });

        // Handle login
        post("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            String username = request.queryParams("username");
            String password = request.queryParams("password");

            if (!authenticate(username, password)) {
                model.put("authenticationFailed", true);
                return ViewUtil.render(request, model, Path.LOGIN);
            }
            model.put("authenticationSucceeded", true);

//            if (request.queryParams("remember-me").equals("on")) {
//                BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
//                textEncryptor.setPassword("secret");
//
//                response.cookie("/", "rembember-me", textEncryptor.encrypt(username+":"+password), 604800, true);
//            }

            User user = userDAO.find(username);
            request.session(true).attribute("currentUser", user);

            if (request.queryParams("loginRedirect") != null) {
                response.redirect(request.queryParams("loginRedirect"));
            }

            response.redirect("/");

            return null;
        });

        post("/logout", (request, response) -> {
            request.session().removeAttribute("currentUser");
            request.session().attribute("loggedOut", true);
            response.redirect(Path.LOGIN);
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
                  return new Gson().toJson(resService.getUserPosts(request.params("username")));
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

