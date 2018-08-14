package util;

import entities.FriendRequest;
import entities.User;
import freemarker.template.Configuration;
import main.Main;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewUtil {


    public static Route notFound = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        Map<String, Object> model = new HashMap<>();
        model.put("code", HttpStatus.NOT_FOUND_404);
        model.put("message", "Sorry, whatever you were looking was eaten by chip.");
        return render(request, model , Path.ERROR);
    };

    public static Route internalServerError = (Request request, Response response) -> {
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        Map<String, Object> model = new HashMap<>();
        model.put("code", HttpStatus.INTERNAL_SERVER_ERROR_500);
        model.put("message", "This is shameful, it seems that Chip was messing with important things ");
        return render(request, model, Path.ERROR);
    };

    public static String render(Request request, Map<String, Object> model, String templatePath) {

        User currentUser = request.session().attribute("currentUser");

        // En register y en login explotaria este codigo sin este if
        if (currentUser != null) {
            List<FriendRequest> friendRequestList = Main.friendRequestDAO.getFriendRequestsByUser(currentUser);
            model.put("friendRequestList", friendRequestList);

            //Inicializo la lista de amigos
            List<User> friends = Main.userDAO.getFriends(currentUser);
            currentUser.setFriendList(friends);
            model.put("friendList", friends);

            // Esto no esta aqui por casualidad moverlo de aqui sin saber que hace puede provocar explosiones
            model.put("suggestedFriendList", Main.userDAO.getSuggestedFriends(currentUser));
        }

//        List<Notification> notificationList = Main.notificationDAO.findByTargetUser(currentUser);
//        model.put("notificationList", notificationList);

        model.put("currentUser", currentUser);

        return getConfiguredEngine().render(new ModelAndView(model, templatePath));
    }

    private static FreeMarkerEngine getConfiguredEngine() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        configuration.setTemplateUpdateDelayMilliseconds(1000);
        return new FreeMarkerEngine(configuration);
    }
}
