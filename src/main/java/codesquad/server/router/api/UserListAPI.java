package codesquad.server.router.api;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.http.exception.client.Http405Exception;
import codesquad.security.SessionManager;
import codesquad.server.router.handler.ResourceHandler;

import static codesquad.server.thread.ThreadManager.session;

public class UserListAPI implements ResourceHandler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.getRequestStartLine().method() != HttpMethod.GET) throw new Http405Exception();
        if (SessionManager.isExist(session.get())) return handleRedirect("/login/index.html");
        return handleRedirect("/user/index.html?id=1");
    }
}
