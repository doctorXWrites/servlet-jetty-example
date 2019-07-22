package com.codeacademy.www;

import com.codeacademy.www.servlets.UserFacingServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import com.codeacademy.www.servlets.SimpleServlet;

public class JettyServerB {

    private Server server;

    public void start() {

        ThreadPool threadPool = new QueuedThreadPool(10, 10, 30000);
        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8091);
        server.setConnectors(new Connector[]{connector});
        ServletContextHandler context = new ServletContextHandler(server, "/");

        context.addServlet(new ServletHolder(UserFacingServlet.class), "/api/getAge");

        try {
            server.start();
            server.join();
        } catch (Exception ex) {

        } finally {
            server.destroy();
        }

    }

    public void stop() throws Exception {
        server.stop();
    }

    public static void main(String[] args) {
        JettyServerB jettyServer = new JettyServerB();
        jettyServer.start();
    }
}