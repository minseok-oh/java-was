package codesquad.security;

import codesquad.http.HttpRequest;

import static codesquad.security.SessionManager.findSession;
import static codesquad.server.thread.ThreadManager.session;

public class SessionFilter {

    public static void filter(HttpRequest httpRequest) {
        session.set(findSession(httpRequest));
    }
}
