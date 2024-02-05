package jv.bazar.amacame.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomErrorException extends RuntimeException{
    private  HttpStatus status;
    private  String message;
    private  Object data;

    @Builder
    public CustomErrorException(HttpStatus status, String message, Object data) {
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }
}



