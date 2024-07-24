package jv.bazar.amacame.dto.res;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@Setter
public class GenericResponseDTO<K> {
    private int code;
    private  String message;
    private  K data;
    private  HttpStatus status;
}
