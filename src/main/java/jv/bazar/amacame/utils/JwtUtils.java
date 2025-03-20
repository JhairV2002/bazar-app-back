package jv.bazar.amacame.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.repositories.AuthenticationTokenRepository;
import jv.bazar.amacame.services.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static jv.bazar.amacame.cons.SecurityConstants.JWT_EXPIRATION_DATE;

@Component
public class JwtUtils {

    private final AuthenticationTokenRepository authenticationTokenRepository;
    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    private final AuthenticationTokenService authenticationTokenService;

    public JwtUtils(AuthenticationTokenService authenticationTokenService, AuthenticationTokenRepository authenticationTokenRepository) {
        this.authenticationTokenService = authenticationTokenService;
        this.authenticationTokenRepository = authenticationTokenRepository;
    }

    public String createToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        String username = authentication.getPrincipal().toString();

        // separar las authorities por coma
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String generatedToken = JWT.create()
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuer(userGenerator)
                .withIssuedAt(new Date())
                .withExpiresAt(JWT_EXPIRATION_DATE)
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        if (authenticationTokenService.isTokenExists(username)){
            throw CustomErrorException.builder()
                    .status(HttpStatus.CONFLICT)
                    .message("El usuario ya tiene un token activo")
                    .data(null)
                    .build();
        }

        authenticationTokenService.saveToken(
                generatedToken,
                username,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(JWT_EXPIRATION_DATE.getTime()));

        return generatedToken;

    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            // requiero el algoritmo y el issuer

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            String existingUnactiveToken = authenticationTokenRepository.findByTokenAndIsActive(token, Boolean.TRUE).getToken();
            if (!existingUnactiveToken.equals(token) && !existingUnactiveToken.isEmpty()){
                throw new JWTVerificationException("Token no válido");
            }
            return verifier.verify(token);
        } catch (JWTVerificationException e){
            // desactivo el token que en base esta activo
            String existingActiveToken = authenticationTokenRepository.findByTokenAndIsActive(token, Boolean.TRUE).getToken();
            if (!existingActiveToken.isEmpty()){
                authenticationTokenService.invalidateToken(token);
            }
            throw CustomErrorException.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Token no válido")
                    .data(null)
                    .build();
        }
    }

    public String extractUsername(DecodedJWT decodedToken){
        return decodedToken.getSubject();
    }

    public Claim getSpecificClaim(DecodedJWT decodedToken, String claim){
        return decodedToken.getClaim(claim);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedToken){
        return decodedToken.getClaims();
    }

}
