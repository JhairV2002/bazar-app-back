package jv.bazar.amacame.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jv.bazar.amacame.dto.req.LoginReqDTO;
import jv.bazar.amacame.dto.req.SignUpUserReqDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.services.AuthenticationTokenService;
import jv.bazar.amacame.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authTest")
@AllArgsConstructor
public class SecurityTestController {
    private final AuthenticationProvider authenticationProvider;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final UserService userService;


        @PostMapping("/login")
        public GenericResponseDTO<String> test(@RequestBody LoginReqDTO loginReqDTO, HttpServletResponse response, HttpServletRequest request, HttpSession session){
            try {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReqDTO.getUsername(), loginReqDTO.getPassword());
                Authentication authentication = authenticationProvider.authenticate(authenticationToken);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                securityContextRepository.saveContext(context, request, response);
                return GenericResponseDTO.<String>builder()
                        .code(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Login successful")
                        .data(null)
                        .build();

            } catch (Exception e) {
                return GenericResponseDTO.<String>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Login failed")
                        .data(null)
                        .build();
            }
        }



//        @PostMapping("/register")
//        public GenericResponseDTO<String> register(@RequestBody SignUpUserReqDTO signUpUserReqDTO){
//                signUpUserReqDTO.setConfirmPassword(signUpUserReqDTO.getPassword());
//               return  userService.createUser(signUpUserReqDTO);
//        }
}
