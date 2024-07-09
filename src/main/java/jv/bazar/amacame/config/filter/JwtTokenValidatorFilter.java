package jv.bazar.amacame.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    JwtUtils jwtUtils;

    public JwtTokenValidatorFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.replace("Bearer ", "");
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            String username = jwtUtils.extractUsername(decodedJWT);

            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            // se setea en el security context holder
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
        }catch (Exception e){
           throw  CustomErrorException.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("No autorizado")
                    .data(e.getMessage())
                    .build();
        }
    }
}
