package codesquad.http.exception.client;

import codesquad.http.constant.HttpStatus;
import codesquad.http.exception.HttpException;

import static codesquad.server.thread.ThreadManager.httpStatus;

public class Http404Exception extends HttpException {
    public Http404Exception() {
        httpStatus.set(HttpStatus.NOT_FOUND);
    }
}
