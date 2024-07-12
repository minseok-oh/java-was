package codesquad.http.element;

import java.util.HashMap;
import java.util.Map;

import static codesquad.utils.StringUtil.CRLF;

public class HttpHeaders {
    private Map<String, HttpHeader> headers = new HashMap<>();

    public HttpHeaders() {}
    public HttpHeaders(Map<String, HttpHeader> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: headers.keySet()) {
            stringBuilder.append(key).append(": ").append(headers.get(key).toString()).append(CRLF);
        }
        return stringBuilder.toString();
    }

    public void appendHeader(String key, String value) {
        if (!headers.containsKey(key)) headers.put(key, new HttpHeader());
        headers.get(key).append(value);
    }

    public HttpHeader getHeader(String key) {
        if (!headers.containsKey(key)) return null;
        return headers.get(key);
    }
}
