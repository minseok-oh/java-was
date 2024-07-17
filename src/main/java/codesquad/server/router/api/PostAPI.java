package codesquad.server.router.api;

import codesquad.domain.entity.Post;
import codesquad.domain.entity.User;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.router.handler.ResourceHandler;

import java.util.Map;

import static codesquad.Main.*;
import static codesquad.server.thread.ThreadManager.session;

public class PostAPI implements ResourceHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        User user = sessionDatabase.getById(session.get());
        Map<String, String> contents = request.getBody().getBodyMap();
        postDatabase.append(new Post(user.userid(), contents.get("title"), contents.get("contents")));
        return handleRedirect("/index.html?id=1");
    }
}
