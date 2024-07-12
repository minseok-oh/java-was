package codesquad.http.exception.server;

import codesquad.http.constant.HttpStatus;
import codesquad.http.exception.HttpException;

import static codesquad.server.thread.ThreadManager.httpStatus;

public class Http500Exception extends HttpException {
    public Http500Exception() {
        httpStatus.set(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
