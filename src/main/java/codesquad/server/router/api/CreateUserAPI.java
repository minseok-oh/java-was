package codesquad.server.router.api;

import codesquad.domain.entity.User;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.server.router.handler.ResourceHandler;

import java.util.HashMap;
import java.util.Map;

import static codesquad.Main.userDatabase;

public class CreateUserAPI implements ResourceHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.getRequestStartLine().method() != HttpMethod.POST) throw new IllegalArgumentException();

        Map<String, String> info = createUserInfo(request.getBody().toString());
        userDatabase.appendUser(new User(info.get("userId"), info.get("nickname"), info.get("password")));
        return handleRedirect("/index.html");
    }

    private Map<String, String> createUserInfo(final String info) throws IllegalArgumentException {
        Map<String, String> result = new HashMap<>();
        String[] parts = info.split("&");
        for (String part: parts) { result.put(part.split("=")[0].strip(), part.split("=")[1].strip()); }
        return result;
    }
}
