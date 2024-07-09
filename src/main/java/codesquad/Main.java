package codesquad;

import codesquad.domain.db.UserDatabase;
import codesquad.server.Server;

public class Main {
    private static final Server server = new Server();
    public static final UserDatabase userDatabase = new UserDatabase();

    public static void main(String[] args) throws Exception {
        server.init();
        server.activate();
    }
}
