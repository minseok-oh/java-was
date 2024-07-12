package codesquad.server.router;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.ResponseBody;
import codesquad.http.element.ResponseStartLine;
import codesquad.http.exception.client.Http400Exception;
import codesquad.http.exception.client.Http404Exception;
import codesquad.server.router.handler.DynamicResourceHandler;
import codesquad.server.router.handler.StaticResourceHandler;

import java.net.URI;
import java.net.URISyntaxException;

public class Router {

    private final StaticResourceHandler staticResourceHandler = new StaticResourceHandler();
    private final DynamicResourceHandler dynamicResourceHandler = new DynamicResourceHandler();

    public HttpResponse handle(HttpRequest httpRequest) throws Exception {
        if (httpRequest.getRequestStartLine().uri() == null) throw new Http400Exception();

        HttpResponse response = dynamicResourceHandler.handle(httpRequest);
        if (response != null) return response;

        response = staticResourceHandler.handle(httpRequest);
        if (response != null) return response;

        throw new Http404Exception();
    }

    public HttpResponse handleException(HttpStatus status) throws URISyntaxException {
        return new HttpResponse(
                new ResponseStartLine(HttpVersion.HTTP_1_1, status),
                new HttpHeaders(),
                new ResponseBody(new URI("/exception/index.html"))
        );
    }
}
