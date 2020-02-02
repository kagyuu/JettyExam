package com.example.jetty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.ShutdownHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

/**
 * Jetty Server.
 *
 * @author atsushihondoh
 */
@Slf4j
@Data
public class MyServer implements Runnable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Server server;

    private int port;

    private String shutdownToken;

    @Override
    public void run() {
        // -- Server
        ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS);
        servletContextHandler.setContextPath("/");

        // -- The handler handls dynamic(REST) contents.
        // Set Jersey ServletContainer
        ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/api/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter(
            "jersey.config.server.provider.packages",
            MyServer.class.getPackageName() + ".resources"
        );

        // Max Upload Size
        servletContextHandler.setMaxFormContentSize(1024 * 1024 * 1024);

        // -- The handler handls static(HTML,CSS,JS) contents.
        final ResourceHandler resourceHandler = new ResourceHandler();

        // Where is the static contents?
        // The following setting is "[JAR]/htdocs". You can specify a directory on the file system, like "/opt/htpdcs".
        String webroot = MyServer.class.getClassLoader().getResource("htdocs").toExternalForm();
        log.info("Static contetns = {}", webroot);
        resourceHandler.setResourceBase(webroot);

        // Don't list files.
        resourceHandler.setDirectoriesListed(false);

        // Welcome file
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        // Don't use cache
        resourceHandler.setCacheControl("no-store,no-cache,must-revalidate");

        // -- make handlers list
        // You must add the hander which handls static contents FIRST.
        HandlerList handlerList = new HandlerList();

        // shutdown from a valid POST Request
        // ex. curl -X POST http://localhost:8080/shutdown?token=imadmin
        handlerList.addHandler(new ShutdownHandler(shutdownToken, false, true));
        // static contents (HTML,CSS,JS)
        handlerList.addHandler(resourceHandler);
        // dynamic contents (REST)
        handlerList.addHandler(servletContextHandler);

        // -- Server
        server = new Server(port);

        // Bind handlers to the server.
        server.setHandler(handlerList);

        // Settings about HTTP
        final HttpConfiguration httpConfig = new HttpConfiguration();

        // Don't show server version on the HTTP-Header
        httpConfig.setSendServerVersion(false);
        final HttpConnectionFactory httpConnFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector httpConnector = new ServerConnector(server, httpConnFactory);
        httpConnector.setPort(port);
        server.setConnectors(new Connector[]{httpConnector});

        try {
            server.start();
            log.info("Server Start : Success");

            server.join();
            log.info("Server Stop : Success");

        } catch (Exception ex) {
            log.info("Server Start : Fail", ex);
        } finally {
            server.destroy();
            log.info("Server Destroyed");
        }
    }

    public void stop() {
        try {
            server.stop();
            log.info("Request Server Stop : Success");
        } catch (Exception ex) {
            log.error("Request Server Stop : Fail", ex);
        }
    }
}
