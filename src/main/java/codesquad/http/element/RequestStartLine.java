package codesquad.http.element;

import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpVersion;

import java.net.URI;

import static codesquad.utils.StringUtil.CRLF;
import static codesquad.utils.StringUtil.SP;

public record RequestStartLine(HttpMethod method, URI uri, HttpVersion version) {
    @Override
    public String toString() {
        return method.toString() + SP +
                uri.getPath() + SP +
                version.getVersion() + CRLF;
    }
}
