package codesquad.server.processor.message;

import codesquad.http.HttpRequest;
import codesquad.http.constant.HttpMethod;
import codesquad.http.constant.HttpVersion;
import codesquad.http.element.HttpHeader;
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
        Map<String, HttpHeader> headers = parseHeaders(emptyLineIndex, message);

        String body = null;
        if (emptyLineIndex != message.length) body = parseBody(emptyLineIndex + 1, message.length, message);
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
        return message.length;
    }

    private static Map<String, HttpHeader> parseHeaders(int end, String[] message) {
        Map<String, HttpHeader> headers = new HashMap<>();
        for (int idx = 1; idx < end; idx++) {
            String[] words = message[idx].split(":");
            String[] values = words[1].split(";");
            parseHeader(words[0].trim(), values, headers);
        }
        return headers;
    }

    private static void parseHeader(final String message, final String[] values, final Map<String, HttpHeader> headers) {
        HttpHeader header = new HttpHeader();
        for (String value: values) header.append(value.trim());
        headers.put(message, header);
    }

    private static String parseBody(int start, int end, String[] message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i < end; i++) stringBuilder.append(message[i]).append(CRLF);
        return stringBuilder.toString();
    }
}
