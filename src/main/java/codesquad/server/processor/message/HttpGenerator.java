package codesquad.server.processor.message;

import codesquad.http.HttpResponse;
import codesquad.http.element.ResponseBody;
import codesquad.server.processor.template.TemplateEngine;

import java.io.*;

import static codesquad.utils.StringUtil.CRLF;

public class HttpGenerator {

    public static byte[] generate(HttpResponse httpResponse) throws IOException {
        String startLine = httpResponse.getResponseStartLine().toString();
        String headers = httpResponse.getHttpHeaders().toString();

        byte[] headerLines = (startLine + headers + CRLF).getBytes();
        byte[] bodyLines = generateFileBody(httpResponse.getResponseBody());
        return concatenate(headerLines, bodyLines);
    }

    private static byte[] generateFileBody(ResponseBody body) throws IOException {
        if (body == null) return new byte[0];

        byte[] result;
        try (InputStream fileInputStream = HttpGenerator.class.getResourceAsStream("/static" + body.getUri().getPath());
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            if (fileInputStream == null) return new byte[0];

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            result = byteArrayOutputStream.toByteArray();
        }
        if (body.getUri().getPath().endsWith(".html")) result = TemplateEngine.convert(result);
        return result;
    }

    private static byte[] concatenate(byte[]... arrays) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (byte[] array : arrays) {
                outputStream.write(array);
            }
            return outputStream.toByteArray();
        }
    }
}
