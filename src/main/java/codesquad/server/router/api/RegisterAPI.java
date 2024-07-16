package codesquad.server.router.api;

import codesquad.domain.entity.User;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.http.exception.client.Http405Exception;
import codesquad.server.router.handler.ResourceHandler;

import java.util.Map;

import static codesquad.Main.userDatabase;
import static codesquad.domain.entity.User.createUserInfo;

public class RegisterAPI implements ResourceHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.getRequestStartLine().method() != HttpMethod.POST) throw new Http405Exception();

        Map<String, String> info = createUserInfo(request.getBody().toString());
        userDatabase.append(new User(info.get("userId"), info.get("nickname"), info.get("password")));
        return handleRedirect("/index.html?id=1");
    }
}
