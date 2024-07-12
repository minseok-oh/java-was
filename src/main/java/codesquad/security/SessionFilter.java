package codesquad.security;

import codesquad.domain.entity.Session;
import codesquad.http.HttpRequest;

import static codesquad.security.SessionManager.findSession;
import static codesquad.server.thread.ThreadManager.sessionVerified;
import static codesquad.server.thread.ThreadManager.threadLocalSession;

public class SessionFilter {

    public static void filter(HttpRequest httpRequest) {
        threadLocalSession.set(findSession(httpRequest));
        sessionVerified.set(verifySession(httpRequest));
    }

    private static boolean verifySession(HttpRequest httpRequest) {
        Session session = findSession(httpRequest);
        return session != null && SessionManager.isExist(session);
    }
}
