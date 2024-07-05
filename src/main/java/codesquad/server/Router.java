package codesquad.server;

import codesquad.constant.Status;
import codesquad.domain.User;
import codesquad.domain.UserData;
import codesquad.http.HttpParser;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static codesquad.http.HttpGenerator.*;

public class Router {
    private final Map<String, String> router = new HashMap<>() {{
        put("/index.html", "/index.html");
        put("/registration", "/register.html");
        put("/register.html", "/registration/index.html");
        put("/create", "/index.html");
    }};

    public static final UserData userData = new UserData();
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    public void send(Socket clientSocket, HttpRequest httpRequest) throws IOException {
        String[] uri = httpRequest.uri().split("\\?");
        String version = httpRequest.httpVersion();
        switch (uri[0]) {
            case "/registration" -> sendRedirect(clientSocket, new HttpResponse(version, Status.FOUND), uri[0]);
            case "/create" -> {
                createUser(uri[1]);
                sendRedirect(clientSocket, new HttpResponse(version, Status.PERMANENTLY), uri[0]);
            }
            default -> sendOk(clientSocket, new HttpResponse(version, Status.OK), uri[0]);
        }
    }

    public void createUser(final String param) {
        String[] params = HttpParser.parseParam(URLDecoder.decode(param, StandardCharsets.UTF_8));
        Map<String, String> paramMap = HttpParser.parseParams(params);
        logger.debug("[ userId: " + paramMap.get("userId") + ", nickname: " + paramMap.get("nickname") + ", password: " +  paramMap.get("password") + " ]");
        userData.appendUser(new User(paramMap.get("userId"), paramMap.get("nickname"), paramMap.get("password")));
    }

    public void sendOk(Socket clientSocket, HttpResponse httpResponse, String path) throws IOException {
        OutputStream clientOutput = clientSocket.getOutputStream();
        createBytes(clientOutput, httpResponse);

        if (router.containsKey(path)) path = router.get(path);
        appendFileBytes(clientOutput, path);
        clientOutput.flush();
    }

    public void sendRedirect(Socket clientSocket, HttpResponse httpResponse, String uri) throws IOException {
        OutputStream clientOutput = clientSocket.getOutputStream();

        createBytes(clientOutput, httpResponse);
        appendRedirectUriBytes(clientOutput, router.get(uri));
        clientOutput.flush();
    }
}
