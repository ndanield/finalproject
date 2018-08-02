package main;

import dao.DAOImpl;
import entities.User;
import org.jasypt.util.password.BasicPasswordEncryptor;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;
import util.Filters;
import util.Path;
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
import java.util.Map;

import static spark.Spark.*;

public class Main {

    // Declare dependencies
    public static DAOImpl<User, String> userDAO;
    public static BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();


    public static void main(String[] args) {
        // Launch Database
        BootStrapServices.getInstance().init();

        // Configure Spark
        staticFiles.location("/public");

        File uploadDir = new File("parcial2UploadImages");
        uploadDir.mkdir();

        staticFiles.externalLocation("parcial2UploadImages");

        //Instantiate dependencies
        userDAO = new DAOImpl<>(User.class);


        // Creating default user if there are none
        if (userDAO.findAll().isEmpty()) {
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
        get("/", (request, response) -> ViewUtil.renderWithUser(request, new HashMap<>(), Path.INDEX));

        get("/wall", (request, response) ->
                ViewUtil.renderWithUser(request, new HashMap<>(), Path.WALL));

        get("/register", (request, response) -> {
            return ViewUtil.render(request, new HashMap<>(), Path.REGISTER);
        });

        post("/register",(request,response)->{
            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

            User user = new User();
            user.setUsername(request.queryParams("username"));
            user.setPassword(encryptor.encryptPassword(request.queryParams("password")));
            user.setName(request.queryParams("personName"));
            user.setLastname(request.queryParams("lastName"));
            user.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(request.queryParams("born")));
            user.setAdministrator(false);

            userDAO.persist(user);
            request.session().attribute("currentUser", user);

            response.redirect("/");
            return null;
        });

        // Serve login
        get("/login", (request, response) ->  ViewUtil.render(request, new HashMap<>(), Path.LOGIN));

        // Handle login
        post("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<>();

            if (request.q)

            if (!authenticate(request.queryParams("username"), request.queryParams("password"))) {
                model.put("authenticationFailed", true);
                return ViewUtil.renderWithUser(request, model, Path.LOGIN);
            }
            model.put("authenticationSucceeded", true);
            User user = userDAO.find(request.queryParams("username"));
            request.session().attribute("currentUser", user);

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

        get("/album", (request, response) -> ViewUtil.renderWithUser(request, new HashMap<>(), Path.ALBUM));

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
        if (username.isEmpty() || password.isEmpty())
            return false;

        User user = userDAO.find(username);

        if (user == null) {
            return false;
        }

        return encryptor.checkPassword(password, user.getPassword());
    }

    // methods used for logging
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

