package codesquad.server.router.api;

import codesquad.domain.entity.Post;
import codesquad.domain.entity.User;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.router.handler.ResourceHandler;

import static codesquad.Main.*;
import static codesquad.server.thread.ThreadManager.threadLocalSession;

public class PostAPI implements ResourceHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        User user = sessionDatabase.getById(threadLocalSession.get());
        String[] contents = request.getBody().toString().split("&");
        postDatabase.append(new Post(user.userid(), contents[0].split("=")[1], contents[1].split("=")[1]));
        return handleRedirect("/index.html?id=1");
    }
}
