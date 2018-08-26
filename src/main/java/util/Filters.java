package util;

import main.Main;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

import static spark.Spark.before;
import static spark.Spark.halt;

public class Filters {

    private static List<String> cities = Arrays.asList("Azua de Compostela","Baní", "Boca chica", "Bonao", "Constanza", "Cotuí", "El Gran Santo Domingo",
            "Esperanza", "Hato Mayor del Rey","Higüey", "Jarabacoa", "La Romana", "Las Matas de Farfán","Mao","Moca","Monte Plata",
            "Nagua","Pedro Brand","Salcedo","San Antonio de Guerra","San Cristóbal","San Felipe de Puerto Plata","San Francisco de Macorís",
            "San Ignacio de Sabaneta","San José de las Matas","San José de Ocoa","San Juan de la Maguana","San Pedro de Macorías",
            "Santa Bárbara de Samaná","Santa Cruz de Barahona","Santa Cruz del Seibo","Santiago de los Caballeros","Sosúa","Tamboril",
            "Villa Altagracia","Villa Bisonó","Yamasá");

    public static void filters() {
        before("/", (request, response) -> {
            if (request.session().attribute("currentUser") == null) {
                request.session(true).attribute("loginRedirect", request.pathInfo());
                response.redirect("/register");
            }
        });

        before("/register", (request, response) -> {
            if (request.requestMethod().equals("POST")) {
                String username = request.queryParams("username");
                String firstName = request.queryParams("firstName");
                String lastName = request.queryParams("lastName");
                String bornDate = request.queryParams("bornDate");
                String city = request.queryParams("city");
                String password = request.queryParams("password");
                String confirmPassword = request.queryParams("confirmPassword");

                if (username == null || firstName == null || lastName == null || bornDate == null || city == null) {
                    halt(400, "Petición invalida. Se intentó enviar campos vacios que eran requeridos.");
                }

                if (username.length() < 4) {
                    halt(400, "Petición invalida. El nombre de usuario debe contener al menos 4 caracteres.");
                }

                if (password.length() < 8 || confirmPassword.length() < 8) {
                    halt(400, "Petición invalida. Las contraseñas deben de contener al menos 8 caracteres.");
                }

                if (Main.userDAO.find(username) != null) {
                    halt(400, "Petición invalida. El nombre de usuario ya esta en uso.");
                }

                if (!password.equals(confirmPassword)) {
                    halt(400, "Petición invalida. Las contraseñas deben coincidir");
                }

                if (!DateUtil.isValidFormat("yyyy-MM-dd", bornDate)) {
                    halt(400, "Petición invalida. La fecha de nacimiento indicada no posee el formator requerido.");
                }

                if (!cities.contains(city))  {
                    halt(400, "Petición invalida. La ciudad ingresada no se encuentra en los registros.");
                }
            }
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
