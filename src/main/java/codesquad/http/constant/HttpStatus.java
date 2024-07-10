package codesquad.http.constant;

public enum HttpStatus {
    OK("200", "OK"),
    PERMANENTLY("301", "Moved Permanently"),
    FOUND("302", "Found");

    private final String code;
    private final String message;

    HttpStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return this.code; }
    public String getMessage() { return this.message; }

    public static HttpStatus getStatus(String code) {
        return switch (code) {
            case "200" -> HttpStatus.OK;
            case "301" -> HttpStatus.PERMANENTLY;
            case "302" -> HttpStatus.FOUND;
            default -> HttpStatus.OK;
        };
    }
}
