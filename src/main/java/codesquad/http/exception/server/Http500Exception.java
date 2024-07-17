package codesquad.http.exception.server;

import codesquad.http.constant.HttpStatus;
import codesquad.http.exception.HttpException;

public class Http500Exception extends HttpException {
    public Http500Exception() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
