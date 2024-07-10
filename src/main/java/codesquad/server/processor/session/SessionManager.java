package codesquad.server.processor.session;

import codesquad.domain.entity.Session;
import codesquad.http.HttpRequest;
import codesquad.http.element.HttpHeader;

import java.util.UUID;

public class SessionManager {
    public static Session createSession() {
        return new Session(UUID.randomUUID().toString());
    }

    public static Session findSession(HttpRequest httpRequest) {
        HttpHeader header = httpRequest.getHeaders().getHeader("Cookie");
        if (header == null) return null;
        return new Session(header.getHeaderValue("SID"));
    }
}
