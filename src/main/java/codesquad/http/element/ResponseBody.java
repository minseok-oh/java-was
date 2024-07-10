package codesquad.http.element;

import codesquad.http.constant.ContentType;

import java.net.URI;

public class ResponseBody {
    URI uri;
    ContentType contentType;

    public ResponseBody(URI uri) {
        this.uri = uri;
        this.contentType = ContentType.getContentType(uri.getPath());
    }

    public URI getUri() { return this.uri; }
}
