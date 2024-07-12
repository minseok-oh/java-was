package codesquad.http.constant;

public enum HttpStatus {
    OK("200", "OK"),
    FOUND("302", "Found"),
    BAD_REQUEST("400", "Bad Request"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error");

    private final String code;
    private final String message;

    HttpStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() { return this.code + " " + this.message; }
    public String getCode() { return this.code; }
    public String getMessage() { return this.message; }

    public static HttpStatus getStatus(String code) {
        return switch (code) {
            case "200" -> HttpStatus.OK;
            case "302" -> HttpStatus.FOUND;
            case "404" -> HttpStatus.NOT_FOUND;
            case "500" -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.OK;
        };
    }
}
