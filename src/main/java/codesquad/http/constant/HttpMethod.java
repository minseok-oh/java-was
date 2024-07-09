package codesquad.http.constant;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    DEFAULT("DEFAULT");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }

    public static HttpMethod getMethod(String method) {
        return switch (method) {
            case "GET" -> HttpMethod.GET;
            case "POST" -> HttpMethod.POST;
            default -> HttpMethod.DEFAULT;
        };
    }
}
