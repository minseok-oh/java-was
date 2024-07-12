package codesquad.server.processor.template;

import codesquad.domain.entity.User;

import static codesquad.Main.sessionDatabase;
import static codesquad.Main.userDatabase;
import static codesquad.server.thread.ThreadManager.httpStatus;
import static codesquad.server.thread.ThreadManager.threadLocalSession;

public class TemplateEngine {
    public static byte[] convert(byte[] response) {
        String responseString = new String(response);
        if (responseString.matches("(?s).*\\{\\{nickname}}.*")) responseString = convertNickName(responseString);
        if (responseString.matches("(?s).*\\{\\{userList}}.*")) responseString = convertUserList(responseString);
        if (responseString.matches("(?s).*\\{\\{exception}}.*")) responseString = convertException(responseString);
        return responseString.getBytes();
    }

    private static String convertNickName(String response) {
        return response.replace("{{nickname}}", sessionDatabase.getById(threadLocalSession.get()).nickName());
    }

    private static String convertUserList(String response) {
        StringBuilder userList = new StringBuilder();
        for (User user : userDatabase.getAll().values()) {
            userList.append("<li class=\"user-item\">\n")
                    .append("<div class=\"user-name\">").append(user.userId()).append("</div>\n")
                    .append("<div class=\"user-nickname\">").append(user.nickName()).append("</div>\n")
                    .append("</li>\n");
        }
        return response.replace("{{userList}}", userList.toString());
    }

    private static String convertException(String response) {
        return response.replace("{{exception}}", httpStatus.get().toString());
    }
}