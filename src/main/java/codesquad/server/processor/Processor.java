package codesquad.server.processor;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.server.processor.message.HttpGenerator;
import codesquad.server.processor.message.HttpParser;
import codesquad.server.processor.session.SessionFilter;

import java.io.IOException;

public class Processor {
    public HttpRequest processRequest(String inputMessage) throws Exception {
        HttpRequest httpRequest = HttpParser.parseHttpRequest(inputMessage);
        return SessionFilter.filter(httpRequest);
    }

    public byte[] processResponse(HttpResponse httpResponse) throws IOException {
        return HttpGenerator.generate(httpResponse);
    }
}
