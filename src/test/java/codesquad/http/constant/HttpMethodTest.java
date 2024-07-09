package codesquad.http.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpMethodTest {

    @Test
    @DisplayName("메서드 이름에 맞는 http 메서드를 반환합니다")
    void getMethodTest() {
        String getMethod = "GET";
        String postMethod = "POST";

        HttpMethod method1 = HttpMethod.getMethod(getMethod);
        HttpMethod method2 = HttpMethod.getMethod(postMethod);

        assertEquals(method1, HttpMethod.GET);
        assertEquals(method2, HttpMethod.POST);
    }
}