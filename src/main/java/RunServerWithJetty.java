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

public class RunServerWithJetty {

    private static final String CONFIG_FILE_PATH = "/app.properties";
    private static final String STATIC_FILES_PATH = "/static";

    public static void main(String[] args) throws Exception {

        ConfigHolder ch = new ConfigHolder(
                new File(RunServerWithJetty.class.getResource(CONFIG_FILE_PATH).getFile()).getAbsolutePath());

        String staticFilePath = new File(RunServerWithJetty.class.getResource(STATIC_FILES_PATH).getFile()).getAbsolutePath();

        File file = new File(RunServerWithJetty.class.getResource(ch.getProperty("app.db.path")).getFile());

        Dao dao = new ToDoModelDao(file.getAbsolutePath());

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

        server.start();
        server.join();
    }
}
