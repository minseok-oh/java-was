package codesquad.server.router.handler;

import codesquad.domain.db.UserDatabase;
import codesquad.domain.entity.User;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.ResponseStartLine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DynamicResourceHandler implements ResourceHandler {

    private final Map<String, Function<HttpRequest, HttpResponse>> handler = new HashMap<>() {{
        put("/registration", (httpRequest) -> handleRedirect("/registration/index.html"));
        put("/user/create", (httpRequest) -> handleCreateUser(httpRequest, "/index.html"));
    }};
    private final UserDatabase userDatabase;

    public DynamicResourceHandler(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

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

    private HttpResponse handleRedirect(final String path) {
        ResponseStartLine responseStartLine = new ResponseStartLine(HttpVersion.HTTP_1_1, HttpStatus.FOUND);
        HttpHeaders headers = createRedirectHeaders(path);
        return new HttpResponse(responseStartLine, headers, null);
    }

    private HttpHeaders createRedirectHeaders(final String path) {
        HttpHeaders headers = new HttpHeaders(new HashMap<>());
        headers.appendHeader("Location", path);
        return headers;
    }

    private HttpResponse handleCreateUser(final HttpRequest request, final String path) {
        Map<String, String> query = createQuery(request.getRequestStartLine().uri().getQuery());
        userDatabase.appendUser(new User(query.get("userId"), query.get("nickname"), query.get("password")));
        return handleRedirect(path);
    }

    private Map<String, String> createQuery(final String query) {
        Map<String, String> result = new HashMap<>();
        String[] parts = query.split("&");
        for (String part: parts) { result.put(part.split("=")[0].strip(), part.split("=")[1].strip()); }
        return result;
    }
}
