package codesquad.server.processor;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.processor.message.HttpGenerator;
import codesquad.server.processor.message.HttpParser;

import java.io.IOException;

public class Processor {
    public HttpRequest processRequest(String inputMessage) throws Exception {
        return HttpParser.parseHttpRequest(inputMessage);
    }

    public byte[] processResponse(HttpResponse httpResponse) throws IOException {
        return HttpGenerator.generate(httpResponse);
    }
}
