package codesquad.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpParserTest {
    @Test
    @DisplayName("param 라인이 들어오면 &로 요소들을 분리한 string 배열을 반환합니다")
    void parseParam() {
        String param = "name=name&password=password&email=email@email.email";
        String[] params = HttpParser.parseParam(param);

        assertEquals(params[0], "name=name");
        assertEquals(params[1], "password=password");
        assertEquals(params[2], "email=email@email.email");
    }

    @Test
    @DisplayName("param 라인이 들어오면 이에 맞는 key value를 가진 Map을 반환합니다")
    void parseParams() {
        String param = "name=name&password=password&email=email@email.email";
        String[] params = HttpParser.parseParam(param);
        Map<String, String> paramMap = HttpParser.parseParams(params);

        assertEquals(paramMap.get("name"), "name");
        assertEquals(paramMap.get("password"), "password");
        assertEquals(paramMap.get("email"), "email@email.email");
    }
}
