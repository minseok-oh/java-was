package codesquad.server.router.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.ResponseStartLine;

import java.util.HashMap;

public interface ResourceHandler {
    HttpResponse handle(final HttpRequest request);

    default HttpResponse handleRedirect(final String path) {
        ResponseStartLine responseStartLine = new ResponseStartLine(HttpVersion.HTTP_1_1, HttpStatus.FOUND);
        HttpHeaders headers = createRedirectHeaders(path);
        return new HttpResponse(responseStartLine, headers, null);
    }

    private HttpHeaders createRedirectHeaders(final String path) {
        HttpHeaders headers = new HttpHeaders(new HashMap<>());
        headers.appendHeader("Location", path);
        return headers;
    }
}
