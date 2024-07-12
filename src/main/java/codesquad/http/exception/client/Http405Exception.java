package codesquad.http.exception.client;

import codesquad.http.constant.HttpStatus;
import codesquad.http.exception.HttpException;

import static codesquad.server.thread.ThreadManager.httpStatus;

public class Http405Exception extends HttpException {
    public Http405Exception() {
        httpStatus.set(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
