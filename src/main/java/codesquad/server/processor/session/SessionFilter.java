package codesquad.server.processor.session;

import codesquad.domain.entity.Session;
import codesquad.http.HttpRequest;
import codesquad.http.element.RequestStartLine;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import static codesquad.Main.sessionDatabase;
import static codesquad.server.processor.session.SessionManager.findSession;

public class SessionFilter {
    private static final Set<String> path = new HashSet<>() {{
        add("/index.html");
        add("/main/index.html");
    }};

    public static HttpRequest filter(HttpRequest httpRequest) throws URISyntaxException {
        String uri = httpRequest.getRequestStartLine().uri().getPath();
        if (!path.contains(uri)) return httpRequest;

        boolean verified = verifySession(httpRequest);
        if (!verified && uri.equals("/main/index.html")) throw new RuntimeException();
        if (verified && uri.equals("/index.html")) return createNewUri(httpRequest);
        return httpRequest;
    }

    private static boolean verifySession(HttpRequest httpRequest) {
        Session session = findSession(httpRequest);
        return session != null && sessionDatabase.isExist(session);
    }

    private static HttpRequest createNewUri(HttpRequest httpRequest) throws URISyntaxException {
        RequestStartLine requestStartLine = new RequestStartLine(
                httpRequest.getRequestStartLine().method(),
                new URI("/main/index.html"),
                httpRequest.getRequestStartLine().version()
        );
        return new HttpRequest(requestStartLine, httpRequest.getHeaders(), httpRequest.getBody());
    }
}
