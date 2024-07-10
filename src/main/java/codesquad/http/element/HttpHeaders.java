package codesquad.http.element;

import java.util.Map;

import static codesquad.utils.StringUtil.CRLF;

public class HttpHeaders {
    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: headers.keySet()) {
            stringBuilder.append(key).append(": ").append(headers.get(key)).append(CRLF);
        }
        return stringBuilder.toString();
    }

    public void appendHeader(String key, String value) {
        headers.put(key, value);
    }
}
