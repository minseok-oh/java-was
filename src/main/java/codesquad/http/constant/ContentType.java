package codesquad.http.constant;

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

    public static ContentType getContentType(final String path) {
        return switch (getFileExtension(path)) {
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

    private static String getFileExtension(String path) {
        if (path == null) return null;
        String fileName = path.substring(path.lastIndexOf('/') + 1);

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) return null;

        return fileName.substring(dotIndex + 1);
    }
}