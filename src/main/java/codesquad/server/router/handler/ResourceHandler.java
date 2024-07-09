package codesquad.server.router.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;

public interface ResourceHandler {
    HttpResponse handle(final HttpRequest request);
}
