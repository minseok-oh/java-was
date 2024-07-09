package codesquad.server.router;

import codesquad.domain.db.UserDatabase;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.router.handler.DynamicResourceHandler;
import codesquad.server.router.handler.StaticResourceHandler;

public class Router {

    private final UserDatabase userDatabase = new UserDatabase();
    private final StaticResourceHandler staticResourceHandler = new StaticResourceHandler(userDatabase);
    private final DynamicResourceHandler dynamicResourceHandler = new DynamicResourceHandler(userDatabase);

    public HttpResponse handle(HttpRequest httpRequest) throws Exception {
        HttpResponse response = dynamicResourceHandler.handle(httpRequest);
        if (response != null) return response;

        response = staticResourceHandler.handle(httpRequest);
        if (response != null) return response;

        throw new IllegalAccessException();
    }

}
