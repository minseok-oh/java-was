package codesquad.http;

import codesquad.http.element.RequestBody;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.RequestStartLine;

import static codesquad.utils.StringUtil.CRLF;

public class HttpRequest {
    private final RequestStartLine requestStartLine;
    private final HttpHeaders headers;
    private final RequestBody body;

    public HttpRequest(RequestStartLine requestStartLine, HttpHeaders headers, RequestBody body) {
        this.requestStartLine = requestStartLine;
        if (requestStartLine == null) throw new IllegalArgumentException("RequestStartLine is null");

        this.headers = headers;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.valueOf(requestStartLine) + headers + CRLF + body;
    }

    public RequestStartLine getRequestStartLine() { return this.requestStartLine; }
    public RequestBody getBody() { return this.body; }
    public HttpHeaders getHeaders() { return this.headers; }
}
