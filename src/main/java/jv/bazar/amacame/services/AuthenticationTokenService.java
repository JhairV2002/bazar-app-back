package jv.bazar.amacame.services;

import jv.bazar.amacame.entity.AuthenticationToken;
import jv.bazar.amacame.repositories.AuthenticationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class AuthenticationTokenService {
    private final AuthenticationTokenRepository authenticationTokenRepository;

    public void saveToken(String token, String username, Timestamp creationDate, Timestamp expirationDate) {
        authenticationTokenRepository.save(AuthenticationToken.builder()
                .token(token)
                .username(username)
                .creationDate(creationDate)
                .expirationDate(expirationDate)
                .isActive(true)
                .build());
    }

    public void invalidateToken(String token) {
        AuthenticationToken authenticationToken = authenticationTokenRepository
                .findByTokenAndIsActive(token, true);
        if (authenticationToken.equals(null)) {
            throw new RuntimeException("Token no encontrado");
        }
        authenticationToken.setActive(false);
        authenticationTokenRepository.save(authenticationToken);

    }


    public boolean isTokenExists(String username) {
        return authenticationTokenRepository.findByUsernameAndIsActive(username, true ) != null;
    }
    public boolean isTokenActive(String token) {
        AuthenticationToken authenticationToken = authenticationTokenRepository.findByToken(token);
        return authenticationToken.isActive();
    }

}
