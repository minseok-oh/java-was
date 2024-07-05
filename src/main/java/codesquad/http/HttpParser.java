package codesquad.http;

import java.util.HashMap;
import java.util.Map;

public class HttpParser {
    public static String[] parseStartLine(final String line) {
        return line.split(" ");
    }

    public static String[] parseHeader(final String line) {
        return line.split(": ", 2);
    }

    public static String[] parseParam(final String line) { return line.split("&"); }

    public static Map<String, String> parseParams(String[] params) {
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            String[] values = param.split("=");
            map.put(values[0], values[1]);
        }
        return map;
    }
}
