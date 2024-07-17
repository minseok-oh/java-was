package codesquad;

import codesquad.domain.DatabaseSource;
import codesquad.domain.db.CommentDatabase;
import codesquad.domain.db.PostDatabase;
import codesquad.domain.db.SessionDatabase;
import codesquad.domain.db.UserDatabase;
import codesquad.server.Server;

public class Main {
    private static final Server server = new Server();
    public static final UserDatabase userDatabase = new UserDatabase();
    public static final SessionDatabase sessionDatabase = new SessionDatabase();
    public static final PostDatabase postDatabase = new PostDatabase();
    public static final CommentDatabase commentDatabase = new CommentDatabase();

    public static void main(String[] args) throws Exception {
        DatabaseSource.init();
        server.init();
        server.activate();
    }
}
