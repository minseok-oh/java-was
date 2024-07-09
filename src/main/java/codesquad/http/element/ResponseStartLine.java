package codesquad.http.element;

import codesquad.http.constant.HttpStatus;
import codesquad.http.constant.HttpVersion;

import static codesquad.utils.StringUtil.CRLF;
import static codesquad.utils.StringUtil.SP;

public record ResponseStartLine(HttpVersion version, HttpStatus status) {
    @Override
    public String toString() {
        return version.getVersion() + SP +
                status.getCode() + SP +
                status.getMessage() + CRLF;
    }
}
