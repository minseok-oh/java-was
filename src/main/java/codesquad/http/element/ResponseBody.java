package codesquad.http.element;

import codesquad.http.constant.ContentType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ResponseBody {
    private final URI uri;
    private final ContentType contentType;
    private final Map<String, String> bodyMap = new HashMap<>();


    public ResponseBody(URI uri) {
        this.uri = uri;
        this.contentType = ContentType.getContentType(uri.getPath());
        if (uri.getQuery() == null) return;

        String[] parts = uri.getQuery().split("&");
        for (String part: parts) {
            String[] keyValue = part.split("=");
            bodyMap.put(keyValue[0], keyValue[1]);
        }
    }

    public URI getUri() { return this.uri; }
    public Map<String, String> getBodyMap() { return bodyMap; }
}
