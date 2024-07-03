package codesquad;

import codesquad.http.HttpParser;
import codesquad.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public record ClientHandler(Socket clientSocket) implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private static final String RESOURCE_PATH = "src/main/resources/static";

    @Override
    public void run() {
        try {
            HttpRequest httpRequest = readHttpMessage(clientSocket);
            logger.debug(httpRequest.toString());
            send(clientSocket, RESOURCE_PATH + httpRequest.uri());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error("클라이언트 소켓을 닫을 수 없습니다.");
            }
        }
    }

    public void send(Socket clientSocket, String path) throws IOException {
        OutputStream clientOutput = clientSocket.getOutputStream();
        String contentType = getContentType(path);

        clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
        clientOutput.write(("Content-Type: " + contentType + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            int data;
            byte[] buffer = new byte[fileInputStream.available()];
            while ((data = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
                clientOutput.write(new String(buffer, 0, data).getBytes());
            }
        }
        clientOutput.flush();
    }

    private String getContentType(final String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }

        return switch (extension) {
            case "html" -> "text/html";
            case "css" -> "text/css";
            case "js" -> "application/javascript";
            case "ico" -> "image/x-icon";
            case "png" -> "image/png";
            case "jpeg", "jpg" -> "image/jpeg";
            case "svg" -> "image/svg+xml";
            default -> "application/octet-stream";
        };
    }

    private HttpRequest readHttpMessage(Socket clientSocket) throws IOException {
        InputStream clientInput = clientSocket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(clientInput);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String startLine = bufferedReader.readLine();
        String[] startLineWords = HttpParser.parseStartLine(startLine);
        HttpRequest httpRequest = new HttpRequest(startLineWords[0], startLineWords[1], startLineWords[2], new HashMap<>());

        while (true) {
            String header = bufferedReader.readLine();
            if (header.isEmpty()) break;

            String[] headerSet = HttpParser.parseHeader(header);
            httpRequest.setHeader(headerSet[0], headerSet[1]);
        }
        return httpRequest;
    }
}
