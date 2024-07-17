package codesquad.http.exception;

import codesquad.http.constant.HttpStatus;

public class HttpException extends RuntimeException {
    protected HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    void setStatus(HttpStatus status) {
        this.status = status;
    }
}
