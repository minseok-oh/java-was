package codesquad.server.router.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.router.api.CreateUserAPI;
import codesquad.server.router.api.RegistrationAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DynamicResourceHandler implements ResourceHandler {

    private final RegistrationAPI registrationHandler = new RegistrationAPI();
    private final CreateUserAPI createUserAPI = new CreateUserAPI();

    private final Map<String, Function<HttpRequest, HttpResponse>> handler = new HashMap<>() {{
        put("/registration", registrationHandler::handle);
        put("/user/create", createUserAPI::handle);
    }};

    @Override
    public HttpResponse handle(final HttpRequest request) {
        String path = request.getRequestStartLine().uri().getPath();
        if (!handler.containsKey(path)) return null;

        Function<HttpRequest, HttpResponse> handle = getHandler(path);
        if (handle == null) return null;

        return handle.apply(request);
    }

    private Function<HttpRequest, HttpResponse> getHandler(final String path) {
        for (String key: handler.keySet()) {
            if (path.startsWith(key)) return handler.get(key);
        }
        return null;
    }
}
