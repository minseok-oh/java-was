package codesquad.server;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.processor.Processor;
import codesquad.server.router.Router;
import codesquad.server.socket.ClientSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8080;
    private static final int THREAD = 10;

    private ServerSocket serverSocket;
    private ExecutorService executor;

    private final Processor processor = new Processor();
    private final Router router = new Router();

    public void init() throws IOException {
        serverSocket = new ServerSocket(PORT);
        executor = Executors.newFixedThreadPool(THREAD);
    }

    public void activate() {
        System.out.println("8080 open!");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.execute(run(new ClientSocket(socket)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Runnable run(ClientSocket clientSocket){
        return () -> {
            try {
                String inputMessage = clientSocket.read();
                HttpRequest httpRequest = processor.processRequest(inputMessage);

                HttpResponse httpResponse = router.handle(httpRequest);
                byte[] outputMessage = processor.processResponse(httpResponse);
                clientSocket.write(outputMessage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
