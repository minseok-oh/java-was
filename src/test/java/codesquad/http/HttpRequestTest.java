package codesquad.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {

    @Test
    @DisplayName("toString 메서드가 요구사항에 맞게 출력되어야 합니다")
    void testToString() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        HttpRequest request = new HttpRequest("GET", "/index.html", "HTTP/1.1", headers);

        String result = request.toString();
        assertEquals("[ method: GET, uri: /index.html, http: HTTP/1.1 ]", result);
    }

    @Test
    @DisplayName("setHeader를 통해 헤더가 추가되어야 합니다")
    void testSetHeader() {
        Map<String, String> headers = new HashMap<>();
        HttpRequest request = new HttpRequest("GET", "/index.html", "HTTP/1.1", headers);

        request.setHeader("User-Agent", "Mozilla/5.0");
        assertEquals("Mozilla/5.0", headers.get("User-Agent"));
    }

}
