package jv.bazar.amacame.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.exceptions.CustomErrorException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component("customAccessDeniedHandler")
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        GenericResponseDTO<String> genericResponse = GenericResponseDTO.<String>builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Algo salió mal :(")
                .data(null)
                .build();

        String customErrorJson = objectMapper.writeValueAsString(genericResponse);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(customErrorJson);
    }
}
