package codesquad.server.router.api;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.http.exception.client.Http405Exception;
import codesquad.server.router.handler.ResourceHandler;

import static codesquad.server.thread.ThreadManager.sessionVerified;

public class UserListAPI implements ResourceHandler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.getRequestStartLine().method() != HttpMethod.GET) throw new Http405Exception();
        if (sessionVerified.get() == null || !sessionVerified.get()) return handleRedirect("/login/index.html");
        return handleRedirect("/user/index.html");
    }
}
