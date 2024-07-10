package codesquad.server.router.api;

import codesquad.domain.entity.Session;
import codesquad.domain.entity.User;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.ResponseStartLine;
import codesquad.server.router.handler.ResourceHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static codesquad.Main.sessionDatabase;
import static codesquad.Main.userDatabase;
import static codesquad.domain.entity.User.createUserInfo;

public class LoginAPI implements ResourceHandler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.getRequestStartLine().method() != HttpMethod.POST) throw new IllegalArgumentException();

        Map<String, String> userInfo = createUserInfo(request.getBody().toString());
        User user = userDatabase.getById(userInfo.get("userId"));
        if (!verifyUserInfo(user, userInfo)) return handleRedirect("/login/login_failed.html");

        Session session = sessionDatabase.append(user);
        return handleRedirect("/main/index.html", session.sid());
    }

    private boolean verifyUserInfo(User user, Map<String, String> userInfo) {
        return !Objects.equals(user, null) &&
                Objects.equals(user.userId(), userInfo.get("userId")) &&
                Objects.equals(user.password(), userInfo.get("password"));
    }

    public HttpResponse handleRedirect(final String path, final String sessionId) {
        ResponseStartLine responseStartLine = new ResponseStartLine(HttpVersion.HTTP_1_1, HttpStatus.FOUND);
        HttpHeaders headers = new HttpHeaders(new HashMap<>());
        headers.appendHeader("Location", path);
        headers.appendHeader("Set-Cookie", "SID=" + sessionId + "; Path=/");
        return new HttpResponse(responseStartLine, headers, null);
    }
}
