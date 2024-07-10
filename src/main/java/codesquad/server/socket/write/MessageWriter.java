package codesquad.server.socket.write;

import java.io.IOException;
import java.io.OutputStream;

import static codesquad.utils.StringUtil.CRLF;

public class MessageWriter {

    public static void write(OutputStream outputStream, byte[] message) throws IOException {
        outputStream.write(message);
        outputStream.write(CRLF.getBytes());
    }
}
