package codesquad.constant;

public enum Status {
    OK("200", "OK"),
    PERMANENTLY("301", "Moved Permanently"),
    FOUND("302", "Found");

    private final String code;
    private final String message;

    Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return this.code; }
    public String getMessage() { return this.message; }
}
