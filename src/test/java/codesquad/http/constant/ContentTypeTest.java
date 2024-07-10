package codesquad.http.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTypeTest {

    @Test
    @DisplayName("해당 경로에 맞는 콘텐츠 타입을 반환합니다")
    void getContentTypeTest() {
        String path = "/test/java/codesquad/http/index.html";
        ContentType contentType = ContentType.getContentType(path);
        assertEquals(contentType, ContentType.HTML);
    }

    @Test
    @DisplayName("유효하지 않은 경로는 디폴트 타입을 반환합니다")
    void getDefaultContentTypeTest() {
        String path = "/test/java/codesquad/http/index.";
        ContentType contentType = ContentType.getContentType(path);
        assertEquals(contentType, ContentType.DEFAULT);
    }
}