package codesquad.http.exception.client;

import codesquad.http.constant.HttpStatus;
import codesquad.http.exception.HttpException;

public class Http400Exception extends HttpException {
    public Http400Exception() {
        status = HttpStatus.BAD_REQUEST;
    }
}
