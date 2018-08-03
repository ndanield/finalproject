package util.Soap;

import com.sun.net.httpserver.HttpContext;
import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpContext;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import util.Soap.ws.SocialWebService;

import javax.xml.ws.Endpoint;
import java.lang.reflect.Method;

public class SoapMain {

    public static void init() throws Exception {
        Server server = new Server(7777);
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
        server.setHandler(contextHandlerCollection);
        server.start();

        //nuestro contexto
        HttpContext context = build(server,"/ws");

        //Servicios brindados agrupando nuestro contexto
        SocialWebService wsc = new SocialWebService();
        Endpoint endpoint = Endpoint.create(wsc);
        endpoint.publish(context);

        //para acceder al servicio es: http://localhost:7777/ws/SocialWebService?wsdl
    }

    private static HttpContext build(Server server, String contextString) throws Exception {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(server, true);
        JettyHttpContext ctx = (JettyHttpContext) jettyHttpServer.createContext(contextString);
        Method method = JettyHttpContext.class.getDeclaredMethod("getJettyContextHandler");
        method.setAccessible(true);
        HttpSpiContextHandler contextHandler = (HttpSpiContextHandler) method.invoke(ctx);
        contextHandler.start();
        return ctx;
    }
}
