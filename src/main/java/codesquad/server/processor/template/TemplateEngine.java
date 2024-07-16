package codesquad.server.processor.template;

import codesquad.domain.entity.Comment;
import codesquad.domain.entity.Post;
import codesquad.domain.entity.User;
import codesquad.http.exception.client.Http404Exception;

import java.util.Map;
import java.util.Objects;

import static codesquad.Main.*;
import static codesquad.server.thread.ThreadManager.threadLocalSession;

public class TemplateEngine {
    public static byte[] convert(byte[] response, String query) {
        String responseString = new String(response);
        if (responseString.matches("(?s).*\\{\\{nickname}}.*")) responseString = convertNickName(responseString);
        if (responseString.matches("(?s).*\\{\\{userList}}.*")) responseString = convertUserList(responseString);
        if (responseString.matches("(?s).*\\{\\{title}}.*")) responseString = convertTitle(responseString, query);
        if (responseString.matches("(?s).*\\{\\{post}}.*")) responseString = convertPost(responseString, query);
        if (responseString.matches("(?s).*\\{\\{commentList}}.*")) responseString = convertCommentList(responseString, query);
        if (responseString.matches("(?s).*\\{\\{account}}.*")) responseString = convertAccount(responseString, query);
        return responseString.getBytes();
    }

    private static String convertAccount(String response, String query) {
        String id = query.split("=")[1];
        Post post = postDatabase.getById(Integer.parseInt(id));
        System.out.println(post.toString());
        User user = userDatabase.getById(post.userid());
        System.out.println(user.toString());
        return response.replace("{{account}}", user.nickname());
    }

    private static String convertCommentList(String response, String query) {
        StringBuilder commentList = new StringBuilder();
        String id = query.split("=")[1];
        Map<Integer, Comment> commentMap = commentDatabase.getAll();
        if (commentMap == null) return response.replace("{{commentList}}", "");

        for (var comment : commentMap.values()) {
            if (Objects.equals(comment.postid(), id)) {
                User user = userDatabase.getById(comment.userid());
                commentList.append("<li class=\"comment-item hidden\">\n")
                        .append("<div class=\"comment__item__user\">\n")
                        .append("<img class=\"comment__item__user__img\" />")
                        .append("<p class=\"comment__item__user__nickname\">").append(user.nickname()).append("</p>\n")
                        .append("</div>\n")
                        .append("<p class=\"comment__item__article\">").append(comment.contents()).append("</p>\n")
                        .append("</li>\n");
            }
        }
        return response.replace("{{commentList}}", commentList.toString());
    }

    private static String convertNickName(String response) {
        return response.replace("{{nickname}}", sessionDatabase.getById(threadLocalSession.get()).nickname());
    }

    private static String convertUserList(String response) {
        StringBuilder userList = new StringBuilder();
        Map<String, User> userMap = userDatabase.getAll();
        if (userMap == null) return response.replace("{{userList}}", "");

        for (User user : userMap.values()) {
            userList.append("<li class=\"user-item\">\n")
                    .append("<div class=\"user-name\">").append(user.userid()).append("</div>\n")
                    .append("<div class=\"user-nickname\">").append(user.nickname()).append("</div>\n")
                    .append("</li>\n");
        }
        return response.replace("{{userList}}", userList.toString());
    }

    private static String convertTitle(String response, String query) {
        String id = query.split("=")[1];
        Post post = postDatabase.getById(Integer.parseInt(id));
        if (post == null) throw new Http404Exception();
        return response.replace("{{title}}", post.title());
    }

    private static String convertPost(String response, String query) {
        String id = query.split("=")[1];
        Post post = postDatabase.getById(Integer.parseInt(id));
        return response.replace("{{post}}", post.contents());
    }
}