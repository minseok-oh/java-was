package codesquad.http.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpStatusTest {

    @Test
    @DisplayName("코드에 맞는 status가 반환되어야 합니다")
    void getStatusTest() {
        String okCode = "200";
        String foundCode = "302";

        HttpStatus httpStatus1 = HttpStatus.getStatus(okCode);
        HttpStatus httpStatus2 = HttpStatus.getStatus(foundCode);

        assertEquals(httpStatus1, HttpStatus.OK);
        assertEquals(httpStatus2, HttpStatus.FOUND);
    }
}