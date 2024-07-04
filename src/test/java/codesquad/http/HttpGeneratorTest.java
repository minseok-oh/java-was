package codesquad.http;

import codesquad.constant.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpGeneratorTest {
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    @DisplayName("해당 http response 객체의 값을 line으로 반환해야 합니다")
    void createBytes() throws IOException {
        HttpResponse httpResponse = new HttpResponse("HTTP/1.1", Status.OK);
        HttpGenerator.createBytes(outputStream, httpResponse);
        String output = outputStream.toString();
        assertEquals(output, "HTTP/1.1 200 OK\r\n");
    }

    @Test
    @DisplayName("해당 path에 맞추어 location 값을 설정하여 반환해야 합니다")
    void appendRedirectUriBytes() throws IOException {
        HttpGenerator.appendRedirectUriBytes(outputStream, "path");
        String output = outputStream.toString();
        assertEquals(output, "Location: path\r\n");
    }

    @Test
    @DisplayName("해당 경로 내의 file을 불러와 값을 설정하여 반환해야 합니다")
    void appendFileBytes() throws IOException {
        HttpGenerator.appendFileBytes(outputStream, "/index.html");
        String output = outputStream.toString();
        System.out.println(output);
    }
}
