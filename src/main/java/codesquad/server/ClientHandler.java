package codesquad.server;

import codesquad.http.HttpParser;
import codesquad.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private static final Router router = new Router();

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            HttpRequest httpRequest = readHttpMessage(clientSocket);
            router.send(clientSocket, httpRequest);
            logger.debug(httpRequest.toString());
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
