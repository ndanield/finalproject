package util;

import dao.NotificationDAO;
import entities.Notification;
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
        List<Notification> notificationList = Main.notificationDAO.findAll();
        model.put("notificationList", notificationList);
        if (request.session().attribute("currentUser") != null) {
            model.put("currentUser", request.session().attribute("currentUser"));
        }
        return getConfiguredEngine().render(new ModelAndView(model, templatePath));
    }

    private static FreeMarkerEngine getConfiguredEngine() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        configuration.setTemplateUpdateDelayMilliseconds(1000);
        return new FreeMarkerEngine(configuration);
    }
}
