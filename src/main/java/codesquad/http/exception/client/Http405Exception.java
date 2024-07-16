package codesquad.http.exception.client;

import codesquad.http.constant.HttpStatus;
import codesquad.http.exception.HttpException;

public class Http405Exception extends HttpException {
    public Http405Exception() {
        status = HttpStatus.METHOD_NOT_ALLOWED;
    }
}
