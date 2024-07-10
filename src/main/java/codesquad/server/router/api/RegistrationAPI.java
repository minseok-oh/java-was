package codesquad.server.router.api;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.server.router.handler.ResourceHandler;

public class RegistrationAPI implements ResourceHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.getRequestStartLine().method() != HttpMethod.GET) throw new IllegalArgumentException();
        return handleRedirect("/registration/index.html");
    }
}
