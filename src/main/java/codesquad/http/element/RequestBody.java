package codesquad.http.element;

import java.util.Map;

import static codesquad.utils.StringUtil.CRLF;

public class RequestBody {
    private final Map<String, String> body;

    public RequestBody(Map<String, String> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: body.keySet()) {
            stringBuilder.append(key).append(body.get(key)).append(CRLF);
        }
        return stringBuilder.toString();
    }
}
