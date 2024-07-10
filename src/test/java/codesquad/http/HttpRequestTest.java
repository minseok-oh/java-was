package codesquad.http;

import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeader;
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

import static codesquad.utils.StringUtil.CRLF;
import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTest {

    private RequestStartLine requestStartLine;
    private HttpHeaders headers;
    private RequestBody body;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        requestStartLine = new RequestStartLine(HttpMethod.GET, new URI("/index.html"), HttpVersion.HTTP_1_1);

        Map<String, HttpHeader> headerMap = new HashMap<>();
        HttpHeader header = new HttpHeader();
        header.append("localhost");
        headerMap.put("Host", header);
        headers = new HttpHeaders(headerMap);
        body = new RequestBody(null);
    }

    @Test
    @DisplayName("요청이 만들어지고 이를 가져올 수 있습니다")
    public void createHttpRequestTest() {
        HttpRequest httpRequest = new HttpRequest(requestStartLine, headers, body);

        assertNotNull(httpRequest);
        assertEquals(requestStartLine, httpRequest.getRequestStartLine());
        assertEquals(headers, httpRequest.getHeaders());
        assertEquals(body, httpRequest.getBody());
    }

    @Test
    @DisplayName("요청을 문자열의 형태로 가져올 수 있습니다")
    public void toStringTest() {
        HttpRequest httpRequest = new HttpRequest(requestStartLine, headers, body);
        String expected = String.valueOf(requestStartLine) + headers + CRLF + body;
        assertEquals(expected, httpRequest.toString());
    }
}