package codesquad.http;

import codesquad.constant.Status;

public record HttpResponse(String httpVersion, Status status) {
}
