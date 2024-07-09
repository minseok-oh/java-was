package codesquad.server.router.handler;

import codesquad.domain.db.UserDatabase;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.ContentType;
import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.ResponseBody;
import codesquad.http.element.ResponseStartLine;

import java.net.URI;
import java.util.HashMap;

public class StaticResourceHandler implements ResourceHandler {
    private final UserDatabase userDatabase;

    public StaticResourceHandler(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public HttpResponse handle(final HttpRequest request) {
        URI uri = request.getRequestStartLine().uri();
        ResponseStartLine responseStartLine = new ResponseStartLine(HttpVersion.HTTP_1_1, HttpStatus.OK);
        ResponseBody body = new ResponseBody(uri);
        HttpHeaders headers = createStaticResourceHeaders(uri.getPath());
        return new HttpResponse(responseStartLine, headers, body);
    }

    private HttpHeaders createStaticResourceHeaders(final String path) {
        HttpHeaders headers = new HttpHeaders(new HashMap<>());
        headers.appendHeader("Content-Type", ContentType.getContentType(path).getContentType());
        return headers;
    }
}
