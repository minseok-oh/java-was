package codesquad.server;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.exception.HttpException;
import codesquad.server.processor.Processor;
import codesquad.server.router.Router;
import codesquad.server.socket.ClientSocket;
import codesquad.server.thread.ThreadManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static codesquad.server.thread.ThreadManager.httpStatus;

public class Server {
    private final int PORT = 8080;
    private final int THREAD = 10;

    private ServerSocket serverSocket;

    private final Processor processor = new Processor();
    private final Router router = new Router();
    private final ThreadManager threadManager = new ThreadManager(THREAD);

    public void init() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public void activate() {
        System.out.println("8080 open!");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                threadManager.execute(run(new ClientSocket(socket)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void start(ClientSocket clientSocket) throws Exception {
        String inputMessage = clientSocket.read();
        HttpRequest httpRequest = processor.processRequest(inputMessage);

        HttpResponse httpResponse = router.handle(httpRequest);
        byte[] outputMessage = processor.processResponse(httpResponse);
        clientSocket.write(outputMessage);
    }

    private Runnable run(ClientSocket clientSocket) {
        return () -> {
            try {
                start(clientSocket);
            } catch (HttpException httpException) {
                handleHttpException(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                threadManager.clear();
                closeClientSocket(clientSocket);
            }
        };
    }

    private void handleHttpException(ClientSocket clientSocket) {
        try {
            HttpResponse httpResponse = router.handleException(httpStatus.get());
            byte[] outputMessage = processor.processResponse(httpResponse);
            clientSocket.write(outputMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void closeClientSocket(ClientSocket clientSocket) {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
