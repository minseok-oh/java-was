package codesquad.server.socket.read;

import java.io.*;

import static codesquad.utils.StringUtil.CRLF;

public class MessageReader {

    public static String read(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder request = new StringBuilder();

        int contentLength = readHeader(reader, request);
        request.append(CRLF);
        if (contentLength > 0) readBody(reader, request, contentLength);

        return request.toString();
    }

    private static int readHeader(BufferedReader reader, StringBuilder request) throws IOException {
        String line;
        int contentLength = 0;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            request.append(line).append(CRLF);
            if (line.startsWith("Content-Length")) contentLength = Integer.parseInt(line.split(":")[1].trim());
        }
        return contentLength;
    }

    private static void readBody(BufferedReader reader, StringBuilder request, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        reader.read(body, 0, contentLength);
        request.append(body);
    }
}
