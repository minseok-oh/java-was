package codesquad.domain.entity;

import java.util.HashMap;
import java.util.Map;

public record User(String userid, String nickname, String password) {

    public static Map<String, String> createUserInfo(final String info) throws IllegalArgumentException {
        Map<String, String> result = new HashMap<>();
        String[] parts = info.split("&");
        for (String part: parts) { result.put(part.split("=")[0].strip(), part.split("=")[1].strip()); }
        return result;
    }
}
