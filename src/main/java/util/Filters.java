package util;

import spark.Request;
import spark.Response;

import static spark.Spark.before;

public class Filters {
    public static void filters() {
        before("/", (request, response) -> {
            if (request.session().attribute("currentUser") == null) {
                request.session(true).attribute("loginRedirect", request.pathInfo());
                response.redirect("/register");
            }
        });
        before("/walls/*", Filters::verifyUserIsLogged);
        before("/album", Filters::verifyUserIsLogged);
    }

    private static void verifyUserIsLogged(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session(true).attribute("loginRedirect", request.pathInfo());
            response.redirect("/login");
        }
    }
}
