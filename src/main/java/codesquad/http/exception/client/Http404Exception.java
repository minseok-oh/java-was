package codesquad.http.exception.client;

import codesquad.http.constant.HttpStatus;
import codesquad.http.exception.HttpException;

public class Http404Exception extends HttpException {
    public Http404Exception() {
        status = HttpStatus.NOT_FOUND;
    }
}
