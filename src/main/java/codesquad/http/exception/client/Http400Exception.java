package codesquad.http.exception.client;

import codesquad.http.constant.HttpStatus;
import codesquad.http.exception.HttpException;

import static codesquad.server.thread.ThreadManager.httpStatus;

public class Http400Exception extends HttpException {
    public Http400Exception() {
        httpStatus.set(HttpStatus.BAD_REQUEST);
    }
}
