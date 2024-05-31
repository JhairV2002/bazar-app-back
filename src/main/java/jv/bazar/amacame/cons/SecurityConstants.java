package jv.bazar.amacame.cons;

import java.util.Date;

public class SecurityConstants {
    public static final int JWT_EXPIRATION = 1000 * 60 * 60;
    public static final Date JWT_EXPIRATION_DATE = new Date(System.currentTimeMillis() + JWT_EXPIRATION);
    public static final String[] WHITE_LIST_URLS = {
            "/auth/**",
            "/login",
            "/swagger-ui/**",
            "/v3/api-docs/**",
//            "/brands/**"
    };
}
