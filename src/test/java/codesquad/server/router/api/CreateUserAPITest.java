package codesquad.server.router.api;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.RequestBody;
import codesquad.http.element.RequestStartLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static codesquad.Main.userDatabase;
import static org.junit.jupiter.api.Assertions.*;

class CreateUserAPITest {
    private CreateUserAPI createUserAPI;

    @BeforeEach
    void setUp() {
        createUserAPI = new CreateUserAPI();
    }

    @Test
    @DisplayName("POST 요청을 처리하고 사용자를 데이터베이스에 추가해야 합니다")
    void handleTest() throws URISyntaxException {
        String requestBody = "userId=testUser&nickname=TestUser&password=1234";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HttpRequest httpRequest = new HttpRequest(
                new RequestStartLine(HttpMethod.POST, new URI("/user/create"), HttpVersion.HTTP_1_1),
                new HttpHeaders(headers),
                new RequestBody(requestBody)
        );

        HttpResponse httpResponse = createUserAPI.handle(httpRequest);
        assertNotNull(userDatabase.getUserById("testUser"));
        assertEquals("HTTP/1.1 302 Found\r\n", httpResponse.getResponseStartLine().toString());
    }

    @Test
    @DisplayName("POST가 아닌 요청일 경우 예외를 발생시켜야 합니다")
    void handleInvalidMethodTest() {
        String requestBody = "userId=testUser&nickname=TestUser&password=1234";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HttpRequest httpRequest = new HttpRequest(
                new RequestStartLine(HttpMethod.GET, URI.create("/user/create"), HttpVersion.HTTP_1_1),
                new HttpHeaders(headers),
                new RequestBody(requestBody)
        );

        assertThrows(IllegalArgumentException.class, () -> createUserAPI.handle(httpRequest));
    }
}