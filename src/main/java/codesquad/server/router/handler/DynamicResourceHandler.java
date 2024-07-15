package codesquad.server.router.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.router.api.LogoutAPI;
import codesquad.server.router.api.RegisterAPI;
import codesquad.server.router.api.LoginAPI;
import codesquad.server.router.api.UserListAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DynamicResourceHandler implements ResourceHandler {

    private final RegisterAPI registerAPI = new RegisterAPI();
    private final LoginAPI loginAPI = new LoginAPI();
    private final LogoutAPI logoutAPI = new LogoutAPI();
    private final UserListAPI userListAPI = new UserListAPI();

    private final Map<String, Function<HttpRequest, HttpResponse>> handler = new HashMap<>() {{
        put("/registration", (HttpRequest) -> handleRedirect("/registration/index.html"));
        put("/login", (HttpRequest) -> handleRedirect("/login/index.html"));
        put("/article", (HttpRequest) -> handleRedirect("/article/index.html"));
        put("/comment", (HttpRequest) -> handleRedirect("/comment/index.html"));
        put("/user/list", userListAPI::handle);
        put("/logout", logoutAPI::handle);
        put("/user/create", registerAPI::handle);
        put("/user/login", loginAPI::handle);
    }};

    @Override
    public HttpResponse handle(final HttpRequest request) {
        String path = request.getRequestStartLine().uri().getPath();
        Function<HttpRequest, HttpResponse> handle = getHandler(path);
        if (handle == null) return null;

        return handle.apply(request);
    }

    private Function<HttpRequest, HttpResponse> getHandler(final String path) {
        for (String key: handler.keySet()) {
            if (path.equals(key)) return handler.get(key);
        }
        return null;
    }
}
