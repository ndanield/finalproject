package util;

import main.Main;
import spark.Request;
import spark.Response;

import static spark.Spark.before;
import static spark.Spark.halt;

public class Filters {

    public static void filters() {
        before("/", (request, response) -> {
            if (request.session().attribute("currentUser") == null) {
                request.session(true).attribute("loginRedirect", request.pathInfo());
                response.redirect("/register");
            }
        });

        before("/register", (req, res) -> {


            String username = req.queryParams("username");
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            String bornDate = req.queryParams("bornDate");
            String city = req.queryParams("city");
            /*if (username == null ||
            firstName == null ||
            lastName == null ||
            bornDate == null ||
            city == null) {
                halt();
            }

            if (Main.userDAO.find(username) != null ) {
                halt();
            }*/
        });

        before("/walls/*", Filters::verifyUserIsLogged);
        before("/post", Filters::verifyUserIsLogged);
//        before("/rest"); NO SE SI TIENE FILTRO
        before("/album", Filters::verifyUserIsLogged);
        before("friendRequest/*", Filters::verifyUserIsLogged);
        before("/userlist", Filters::verifyUserIsLogged);
//        before("/*", Filters::verifyUserIsLogged);
    }

    private static void verifyUserIsLogged(Request request, Response response) {
//        if (request.pathInfo().equals("/login") || request.pathInfo().equals("/register")) {
//            return;
//        }

        if (request.session().attribute("currentUser") == null) {
            request.session(true).attribute("loginRedirect", request.pathInfo());
            response.redirect("/login");
            halt();
        }
    }
}
