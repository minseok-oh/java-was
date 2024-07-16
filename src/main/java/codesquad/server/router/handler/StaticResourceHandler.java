package codesquad.server.router.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.ContentType;
import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.ResponseBody;
import codesquad.http.element.ResponseStartLine;
import codesquad.http.exception.client.Http400Exception;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static codesquad.server.thread.ThreadManager.sessionVerified;

public class StaticResourceHandler implements ResourceHandler {
    private final Set<String> path = new HashSet<>() {{
        add("/index.html");
        add("/main/index.html");
    }};

    @Override
    public HttpResponse handle(final HttpRequest request) {
        URI uri = createVerifiedUri(request.getRequestStartLine().uri());
        if (!sessionVerified.get() && uri.getPath().equals("/article/index.html")) return handleRedirect("/login/index.html");
        if (uri == null) return handleRedirect("/main/index.html?id=1");
        if (uri.getPath().equals("/index.html") && uri.getQuery() == null) return handleRedirect("/index.html?id=1");

        ResponseStartLine responseStartLine = new ResponseStartLine(HttpVersion.HTTP_1_1, HttpStatus.OK);
        ResponseBody body = new ResponseBody(uri);
        HttpHeaders headers = createStaticResourceHeaders(uri.getPath());
        return new HttpResponse(responseStartLine, headers, body);
    }

    private URI createVerifiedUri(final URI uri) {
        if (!path.contains(uri.getPath())) return uri;
        if (!sessionVerified.get() && uri.getPath().equals("/main/index.html")) throw new Http400Exception();
        if (sessionVerified.get() && uri.getPath().equals("/index.html")) return null;
        return uri;
    }

    private HttpHeaders createStaticResourceHeaders(final String path) {
        HttpHeaders headers = new HttpHeaders(new HashMap<>());
        headers.appendHeader("Content-Type", ContentType.getContentType(path).getContentType());
        return headers;
    }
}
