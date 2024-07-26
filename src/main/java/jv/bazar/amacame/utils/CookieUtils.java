package jv.bazar.amacame.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieUtils {

    public void createCookie(String name, String jwt, int maxAge, HttpServletResponse response) {
        ResponseCookie responseCookie =  ResponseCookie.from(name, jwt)
                .maxAge(maxAge)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .domain("localhost")
                .build();
        response.setHeader("Set-Cookie", responseCookie.toString());
        response.setHeader("Access-Control-Allow-Credentials", "true");

    }

    public void deleteCookie(String name, HttpServletResponse response) {
        ResponseCookie responseCookie = ResponseCookie.from(name, "")
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .build();
        response.setHeader("Set-Cookie", responseCookie.toString());
    }
}
