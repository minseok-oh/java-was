package codesquad.http.element;

public class RequestBody {
    private final String body;

    public RequestBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        if (body == null) return "";
        return body;
    }
}
