package codesquad.server.router.api;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.RequestBody;
import codesquad.http.element.RequestStartLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationAPITest {
    private final RegistrationAPI registrationAPI = new RegistrationAPI();

    @Test
    @DisplayName("GET 요청을 처리하고 사용자를 리다이렉트해야 합니다")
    void handleTest() throws URISyntaxException {
        Map<String, String> headers = new HashMap<>();
        HttpRequest httpRequest = new HttpRequest(
                new RequestStartLine(HttpMethod.GET, new URI("/registration/index.html"), HttpVersion.HTTP_1_1),
                new HttpHeaders(headers),
                new RequestBody(null)
        );

        HttpResponse httpResponse = registrationAPI.handle(httpRequest);
        assertEquals("HTTP/1.1 302 Found\r\n", httpResponse.getResponseStartLine().toString());
    }
}