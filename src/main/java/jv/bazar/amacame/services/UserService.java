package jv.bazar.amacame.services;

import jakarta.servlet.http.HttpServletResponse;
import jv.bazar.amacame.config.ApplicationConfig;
import jv.bazar.amacame.cons.SecurityConstants;
import jv.bazar.amacame.dto.req.AuthLoginRequestDTO;
import jv.bazar.amacame.dto.req.SignUpUserReqDTO;
import jv.bazar.amacame.dto.res.AuthLoginResponseDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.entity.Roles;
import jv.bazar.amacame.entity.User;
import jv.bazar.amacame.enums.RoleEnum;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.mappers.UsersMapper;
import jv.bazar.amacame.repositories.RolesRepository;
import jv.bazar.amacame.repositories.UserRepository;
import jv.bazar.amacame.utils.CookieUtils;
import jv.bazar.amacame.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;
    private final RolesRepository rolesRepository;
    private final JwtUtils jwtUtils;
    private final ApplicationConfig applicationConfig;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtils cookieUtils;

    public GenericResponseDTO<String> createUser(SignUpUserReqDTO signUpUserReqDTO) throws CustomErrorException {
        if (userRepository.findByEmail(signUpUserReqDTO.getEmail()).isPresent()) {
            throw  CustomErrorException.builder()
                    .status(HttpStatus.CONFLICT)
                    .message("El email ya se encuentra registrado")
                    .data(null)
                    .build();
        }
        try {
            User user = usersMapper.userReqDtoToUser(signUpUserReqDTO);
            Set<Roles> rolesSet = rolesRepository.findRolesByRoleEnumIn(signUpUserReqDTO.getRoles());
            if(rolesSet.isEmpty()){
                throw  CustomErrorException.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("No se han encontrado roles")
                        .data(null)
                        .build();
            }
            user.setRoles(rolesSet);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User createdUser = userRepository.save(user);
            // login user
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    createdUser.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())))
                    .toList();

            // Authorities
            createdUser.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                    .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
            // se realiza el logeo

            Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getEmail(), null, authorities);

            return GenericResponseDTO.<String>builder().code(201).message("Usuario creado y logueado con éxito").data(
                    null
            ).status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw  CustomErrorException.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Error al crear el usuario")
                    .data(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    public void loginUser(AuthLoginRequestDTO authLoginRequestDTO, HttpServletResponse response) {
        try {
        String username = authLoginRequestDTO.username();
        String password = authLoginRequestDTO.password();
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =  jwtUtils.createToken(authentication);
        cookieUtils.createCookie("jwtToken", token, SecurityConstants.JWT_EXPIRATION, response);
        } catch (Exception e) {
            throw  CustomErrorException.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Error al iniciar sesión")
                    .data(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = applicationConfig.userDetailsService().loadUserByUsername(username);

        if(userDetails == null){
            throw  CustomErrorException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Usuario o contraseña incorrecta.")
                    .data(null)
                    .build();
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw  CustomErrorException.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Usuario o contraseña incorrecta.")
                    .data(null)
                    .build();
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
