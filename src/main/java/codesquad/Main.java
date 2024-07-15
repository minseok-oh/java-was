package codesquad;

import codesquad.domain.connect.DatabaseConnector;
import codesquad.domain.db.SessionDatabase;
import codesquad.domain.db.UserDatabase;
import codesquad.server.Server;

public class Main {
    private static final Server server = new Server();
    public static final UserDatabase userDatabase = new UserDatabase();
    public static final SessionDatabase sessionDatabase = new SessionDatabase();

    public static void main(String[] args) throws Exception {
        DatabaseConnector.init();
        server.init();
        server.activate();
    }
}
