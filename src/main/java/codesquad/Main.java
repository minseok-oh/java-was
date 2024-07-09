package codesquad;

import codesquad.server.Server;

public class Main {
    private static final Server server = new Server();

    public static void main(String[] args) throws Exception {
        server.init();
        server.activate();
    }
}
