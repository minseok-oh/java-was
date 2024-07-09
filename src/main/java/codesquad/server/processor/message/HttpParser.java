package codesquad.server.processor.message;

import codesquad.http.HttpRequest;
import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.RequestBody;
import codesquad.http.element.HttpHeaders;
import codesquad.http.element.RequestStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static codesquad.utils.StringUtil.CRLF;
import static codesquad.utils.StringUtil.SP;

public class HttpParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpParser.class);

    public static HttpRequest parseHttpRequest(final String inputMessage) throws Exception {
        String[] message = inputMessage.split(CRLF);
        RequestStartLine requestStartLine = parseStartLine(message[0]);
        logger.debug(requestStartLine.toString());

        int emptyLineIndex = findEmptyLineIndex(message);
        Map<String, String> headers = parseLines(1, emptyLineIndex, message);

        Map<String, String> body = null;
        if (emptyLineIndex != -1) body = parseLines(emptyLineIndex + 1, message.length, message);

        return new HttpRequest(requestStartLine, new HttpHeaders(headers), new RequestBody(body));
    }

    private static RequestStartLine parseStartLine(final String startLine) throws Exception {
        String[] words = startLine.split(SP);
        return new RequestStartLine(HttpMethod.getMethod(words[0]), new URI(words[1]), HttpVersion.HTTP_1_1);
    }

    private static int findEmptyLineIndex(String[] message) {
        for (int i = 1; i < message.length; i++) {
            if (message[i].isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    private static Map<String, String> parseLines(int start, int end, String[] message) {
        Map<String, String> lines = new HashMap<>();
        for (int idx = start; idx < end; idx++) {
            String[] words = message[idx].split(": ");
            lines.put(words[0], words[1]);
        }
        return lines;
    }
}
