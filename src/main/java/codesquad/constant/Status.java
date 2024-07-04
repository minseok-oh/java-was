package codesquad.constant;

public enum Status {
    OK("200", "OK"),
    FOUND("302", "FOUND");

    private final String code;
    private final String message;

    Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return this.code; }
    public String getMessage() { return this.message; }
}
