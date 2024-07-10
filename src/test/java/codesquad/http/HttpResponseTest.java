package codesquad.http;

import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {
    private ResponseStartLine responseStartLine;
    private HttpHeaders headers;
    private ResponseBody body;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        responseStartLine = new ResponseStartLine(HttpVersion.HTTP_1_1, HttpStatus.OK);

        Map<String, HttpHeader> headerMap = new HashMap<>();
        HttpHeader header = new HttpHeader();
        header.append("3");
        headerMap.put("Content-Length", header);
        headers = new HttpHeaders(headerMap);
        body = new ResponseBody(new URI("/index.html"));
    }

    @Test
    @DisplayName("요청이 만들어지고 이를 가져올 수 있습니다")
    public void createHttpRequestTest() {
        HttpResponse httpResponse = new HttpResponse(responseStartLine, headers, body);

        assertNotNull(httpResponse);
        assertEquals(responseStartLine, httpResponse.getResponseStartLine());
        assertEquals(headers, httpResponse.getHttpHeaders());
    }
}