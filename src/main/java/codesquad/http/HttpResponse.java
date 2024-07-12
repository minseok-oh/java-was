package codesquad.http;

import codesquad.http.element.HttpHeaders;
import codesquad.http.element.ResponseBody;
import codesquad.http.element.ResponseStartLine;

public class HttpResponse {
    private final ResponseStartLine responseStartLine;
    private final HttpHeaders headers;
    private final ResponseBody body;

    public HttpResponse(ResponseStartLine responseStartLine, HttpHeaders headers, ResponseBody body) {
        this.responseStartLine = responseStartLine;
        if (responseStartLine == null) throw new IllegalArgumentException("ResponseStartLine is null");

        this.headers = headers;
        this.body = body;
    }

    public ResponseStartLine getResponseStartLine() { return this.responseStartLine; }
    public HttpHeaders getHttpHeaders() { return this.headers; }
    public ResponseBody getResponseBody() { return this.body; }
}
