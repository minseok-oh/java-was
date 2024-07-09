package codesquad.server.socket.read;

import java.io.*;

public class MessageReader {

    public static String read(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder request = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            request.append(line)
                    .append("\r\n");
        }
        return request.toString();
    }
}
