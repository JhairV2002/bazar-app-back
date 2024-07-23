package jv.bazar.amacame.controllers;

import jv.bazar.amacame.dto.req.AuthLoginRequestDTO;
import jv.bazar.amacame.dto.res.AuthLoginResponseDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.services.AuthenticationTokenService;
import jv.bazar.amacame.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AuthenticationController {

    private final  UserService userService;
    private final AuthenticationTokenService authenticationTokenService;

    @PostMapping("/login")
    public GenericResponseDTO<AuthLoginResponseDTO> login(@RequestBody AuthLoginRequestDTO loginRequest) {
        try {
            return GenericResponseDTO.<AuthLoginResponseDTO>builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Sesión iniciada correctamente")
                    .data(userService.loginUser(loginRequest))
                    .build();
        } catch (Exception e) {
            return GenericResponseDTO.<AuthLoginResponseDTO>builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .status(HttpStatus.UNAUTHORIZED)
                    .message(e.getMessage())
                    .data(null)
                    .build();
        }

    }

    @PostMapping("/logout")
    public GenericResponseDTO<String> logout(@RequestHeader("Authorization") String token){
        try {
            authenticationTokenService.invalidateToken(token);
            return GenericResponseDTO.<String>builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Se cerró la sesión correctamente")
                    .data(null)
                    .build();
        } catch (Exception e) {
            return GenericResponseDTO.<String>builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .status(HttpStatus.UNAUTHORIZED)
                    .message(e.getMessage())
                    .data(null)
                    .build();
        }

    }


}
