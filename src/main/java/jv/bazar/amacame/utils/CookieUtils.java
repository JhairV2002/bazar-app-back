package jv.bazar.amacame.utils;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieUtils {

    public ResponseCookie createCookie(String name, String value, Long maxAge) {
        return ResponseCookie.from(name, value)
                .maxAge(maxAge)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public ResponseCookie deleteCookie(String name) {
        return ResponseCookie.from(name, "")
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
