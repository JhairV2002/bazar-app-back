package jv.bazar.amacame.exceptions;

import jv.bazar.amacame.dto.res.GenericResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ AuthenticationException.class})
    @ResponseBody
    public GenericResponseDTO<?> handleAuthenticationException(AuthenticationException e) {
        return GenericResponseDTO.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("No autorizado")
                .data(e.getMessage())
                .build();
    }
}
