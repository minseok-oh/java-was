package codesquad.server.router.api;

import codesquad.domain.entity.Session;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.ResponseStartLine;
import codesquad.http.exception.client.Http400Exception;
import codesquad.http.exception.client.Http405Exception;
import codesquad.security.SessionManager;
import codesquad.server.router.handler.ResourceHandler;

import java.util.HashMap;

import static codesquad.Main.sessionDatabase;

public class LogoutAPI implements ResourceHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.getRequestStartLine().method() != HttpMethod.GET) throw new Http405Exception();

        String sessionId = request.getHeaders().getHeader("Cookie").getHeaderValue("SID");
        Session session = new Session(sessionId);
        if (sessionId == null && !SessionManager.isExist(session)) throw new Http400Exception();

        sessionDatabase.deleteById(session);
        return handleRedirect("/index.html?id=1");
    }

    public HttpResponse handleRedirect(final String path) {
        ResponseStartLine responseStartLine = new ResponseStartLine(HttpVersion.HTTP_1_1, HttpStatus.FOUND);
        HttpHeaders headers = new HttpHeaders(new HashMap<>());
        headers.appendHeader("Location", path);
        headers.appendHeader("Set-Cookie", "SID=; Max-Age=0");
        return new HttpResponse(responseStartLine, headers, null);
    }
}
