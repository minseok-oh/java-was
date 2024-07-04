package codesquad.constant;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/javascrupt"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPEG("image/jpeg"),
    SVG("image/svg+xml"),
    DEFAULT("application/octet-stream")
    ;

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public static ContentType createContentType(final String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }

        return switch (extension) {
            case "html" -> ContentType.HTML;
            case "css" -> ContentType.CSS;
            case "js" -> ContentType.JS;
            case "ico" -> ContentType.ICO;
            case "png" -> ContentType.PNG;
            case "jpeg", "jpg" -> ContentType.JPEG;
            case "svg" -> ContentType.SVG;
            default -> ContentType.DEFAULT;
        };
    }
}