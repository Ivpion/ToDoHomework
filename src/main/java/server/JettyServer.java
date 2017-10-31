package server;

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

import java.io.File;
import java.io.IOException;

public class JettyServer {
    private static final String CONFIG_FILE_PATH = "/app.properties";
    private static final String STATIC_FILES_PATH = "/static";


    public void startServer() throws Exception {
        ConfigHolder ch = createConfigHolder();
        String staticFilePath = createStaticFilePath();
        Dao dao = createDao(ch);
        Server server = createServer(dao, ch, staticFilePath);
        server.start();
        server.join();
    }

    private ConfigHolder createConfigHolder() throws IOException {
       return new ConfigHolder(
                new File(JettyServer.class.getResource(CONFIG_FILE_PATH).getFile()).getAbsolutePath());
    }
    private String createStaticFilePath(){
        return new File(JettyServer.class.getResource(STATIC_FILES_PATH).getFile()).getAbsolutePath();
    }

    private Dao createDao(ConfigHolder ch) throws IOException {
        File file = new File(JettyServer.class.getResource(ch.getProperty("app.db.path")).getFile());
        return new ToDoModelDao(file.getAbsolutePath());
    }



    private Server createServer(Dao dao, ConfigHolder ch, String staticFilePath){

        Server server = new Server(Integer.parseInt(ch.getProperty("app.port")));

        server.setErrorHandler(new ErrorHandler());

        HandlerList handlers = new HandlerList();

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
        resource_handler.setResourceBase(staticFilePath);


        ContextHandler contextCreate = new ContextHandler("/api/create");
        contextCreate.setAllowNullPathInfo(true);
        contextCreate.setHandler(new CreateHandler(dao));


        ContextHandler contextRead = new ContextHandler("/api/read");
        contextRead.setHandler(new ReadHandler(dao));

        ContextHandler contextUpdate = new ContextHandler("/api/update");
        contextUpdate.setAllowNullPathInfo(true);
        contextUpdate.setHandler(new UpdateHandler(dao));

        ContextHandler contextDelete = new ContextHandler("/api/delete");
        contextDelete.setAllowNullPathInfo(true);
        contextDelete.setHandler(new DeleteHandler(dao));

        ContextHandler contextReadAll = new ContextHandler("/api/list");
        contextReadAll.setHandler(new ReadAllHandler(dao));

        handlers.setHandlers(new Handler[]{resource_handler
                ,contextCreate
                ,contextRead
                ,contextUpdate
                ,contextDelete
                ,contextReadAll});

        server.setHandler(handlers);

        return server;

    }



}
