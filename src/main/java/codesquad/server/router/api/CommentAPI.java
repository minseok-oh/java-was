package codesquad.server.router.api;

import codesquad.domain.entity.Comment;
import codesquad.domain.entity.User;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.router.handler.ResourceHandler;

import static codesquad.Main.*;
import static codesquad.server.thread.ThreadManager.threadLocalSession;

public class CommentAPI implements ResourceHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        User user = sessionDatabase.getById(threadLocalSession.get());
        String[] uriSplit = request.getBody().toString().split("&");
        commentDatabase.append(new Comment(user.userid(), uriSplit[0].split("=")[1], uriSplit[1].split("=")[1]));
        return handleRedirect("/main/index.html?id=" + uriSplit[0].split("=")[1]);
    }
}
