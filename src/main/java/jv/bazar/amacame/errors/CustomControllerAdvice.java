package jv.bazar.amacame.errors;


import jv.bazar.amacame.exceptions.CustomErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomDataNotFoundException(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse error = ErrorResponse.builder()
                .status(status)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(error, status);
    }
}
