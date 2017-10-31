
import server.JettyServer;

public class RunServerWithJetty {
    public static void main(String[] args) throws Exception {
        new JettyServer().startServer();
    }
}