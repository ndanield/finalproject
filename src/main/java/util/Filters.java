package util;

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
        before("/*", Filters::verifyUserIsLogged);
    }

    private static void verifyUserIsLogged(Request request, Response response) {
        if (request.pathInfo().equals("/login") || request.pathInfo().equals("/register")) {
            return;
        }

        if (request.session().attribute("currentUser") == null) {
            request.session(true).attribute("loginRedirect", request.pathInfo());
            response.redirect("/login");
            halt();
        }
    }
}
