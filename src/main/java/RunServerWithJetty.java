import config.ConfigHolder;
import dao.Dao;
import dao.ToDoModelDao;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import rest.*;
import server.JettyServer;


import java.io.File;

public class RunServerWithJetty {
    public static void main(String[] args) throws Exception {
        new JettyServer().startServer();
    }
}