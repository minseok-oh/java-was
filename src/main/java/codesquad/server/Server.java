package codesquad.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private static final int PORT = 8080;

    private ServerSocket serverSocket;
    private ExecutorService executor;

    public void setUp() throws IOException {
        serverSocket = new ServerSocket(PORT);
        executor = Executors.newFixedThreadPool(10);
        logger.debug("Listening for connection on port 8080 ....");
    }

    public void receive() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
