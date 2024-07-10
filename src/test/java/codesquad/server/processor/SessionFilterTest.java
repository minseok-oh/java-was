package codesquad.server.processor;

import codesquad.domain.entity.Session;
import codesquad.domain.entity.User;
import codesquad.http.HttpRequest;
import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeader;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.RequestStartLine;
import codesquad.server.processor.session.SessionFilter;
import codesquad.server.processor.session.SessionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static codesquad.Main.sessionDatabase;
import static org.junit.jupiter.api.Assertions.*;

public class SessionFilterTest {

    @Test
    @DisplayName("유효하지 않은 cookie 사용자는 exception이 되어야 합니다")
    void testNotVerifiedCookie() throws URISyntaxException {
        RequestStartLine requestStartLine = new RequestStartLine(HttpMethod.GET, new URI("/main/index.html"), HttpVersion.HTTP_1_1);
        Session session = SessionManager.createSession();
        HttpHeaders headers = createHeaders(session);

        HttpRequest httpRequest = new HttpRequest(requestStartLine, headers,null);
        try {
            SessionFilter.filter(httpRequest);
        } catch (Exception e) {
            assertInstanceOf(RuntimeException.class, e);
        }
    }

    @Test
    @DisplayName("유효한 cookie 사용자는 index.html을 접근 시 main/index.html로 가야 합니다")
    void testVerifiedCookie() throws URISyntaxException {
        RequestStartLine requestStartLine = new RequestStartLine(HttpMethod.GET, new URI("/index.html"), HttpVersion.HTTP_1_1);
        Session session = sessionDatabase.append(new User(null, null, null));
        HttpHeaders headers = createHeaders(session);

        HttpRequest httpRequest = new HttpRequest(requestStartLine, headers,null);
        HttpRequest filteredRequest = SessionFilter.filter(httpRequest);
        assertEquals(filteredRequest.getRequestStartLine().uri().getPath(), "/main/index.html");
    }

    private HttpHeaders createHeaders(Session session) {
        HttpHeader header = new HttpHeader();
        header.append("SID=" + session.sid());
        Map<String, HttpHeader> headerMap = new HashMap<>();
        headerMap.put("Cookie", header);
        return new HttpHeaders(headerMap);
    }
}
