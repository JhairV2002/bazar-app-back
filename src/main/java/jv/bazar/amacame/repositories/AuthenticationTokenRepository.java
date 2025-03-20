package jv.bazar.amacame.repositories;

import jv.bazar.amacame.entity.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByToken(String token);
    AuthenticationToken findByTokenAndIsActive(String token, boolean active);
    AuthenticationToken findByUsernameAndIsActive(String username, boolean active);
}
