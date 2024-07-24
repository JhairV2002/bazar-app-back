package jv.bazar.amacame.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jv.bazar.amacame.dto.req.AuthLoginRequestDTO;
import jv.bazar.amacame.dto.res.AuthLoginResponseDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.services.AuthenticationTokenService;
import jv.bazar.amacame.services.UserService;
import jv.bazar.amacame.utils.CookieUtils;
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
    private final CookieUtils cookieUtils;

    @PostMapping("/login")
    public GenericResponseDTO<String> login(@RequestBody AuthLoginRequestDTO loginRequest, HttpServletResponse response) {
        try {
             userService.loginUser(loginRequest, response);
            return GenericResponseDTO.<String>builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Sesión iniciada correctamente")
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

    @PostMapping("/logout")
    public GenericResponseDTO<String> logout(@CookieValue(name = "jwtToken") String jwtToken,
                                             HttpServletResponse response) {
        try {
            authenticationTokenService.invalidateToken(jwtToken);
            cookieUtils.deleteCookie("jwtToken", response);
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
