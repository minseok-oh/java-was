package codesquad.http;

import codesquad.constant.ContentType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpGenerator {
    private static final String RESOURCE_PATH = "src/main/resources/static";

    public static void createBytes(OutputStream clientOutput, HttpResponse httpResponse) throws IOException {
        clientOutput.write((httpResponse.httpVersion() + " "
                + httpResponse.status().getCode() + " "
                + httpResponse.status().getMessage() + "\r\n").getBytes());
    }

    public static void appendRedirectUriBytes(OutputStream clientOutput, final String path) throws IOException {
        clientOutput.write(("Location: " + path + "\r\n").getBytes());
    }

    public static void appendFileBytes(OutputStream clientOutput, final String path) throws IOException {
        ContentType contentType = ContentType.createContentType(path);
        clientOutput.write(("Content-Type: " + contentType.getContentType() + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());

        try (FileInputStream fileInputStream = new FileInputStream(RESOURCE_PATH + path)) {
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                clientOutput.write(buffer, 0, bytesRead);
            }
        }
    }
}