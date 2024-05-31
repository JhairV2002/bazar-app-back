package jv.bazar.amacame.dto.res;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
public class GenericResponseDTO<K> {
    private int code;
    private  String message;
    private  K data;
    private  HttpStatus status;
}
