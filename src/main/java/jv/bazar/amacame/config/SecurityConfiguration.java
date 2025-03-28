package jv.bazar.amacame.config;

import jv.bazar.amacame.config.filter.JwtTokenValidatorFilter;
import jv.bazar.amacame.enums.RoleEnum;
import jv.bazar.amacame.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtUtils jwtUtils;
    @Qualifier("customAccessDeniedHandler")
    private final CustomAccessDeniedHandler accessDeniedHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(
                    cors -> {
                        cors.configurationSource(new CorsCustomConfiguration());
                    }
                )
                .sessionManagement(sessionManagement -> sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(req -> {
                    req.requestMatchers( "/auth/**").permitAll();
                    req.requestMatchers(HttpMethod.GET,  "/brands/**",  "/products/**", "/promos/**", "/bills/**").hasAnyRole(RoleEnum.ADMIN.name());
                    req.requestMatchers(HttpMethod.POST, "/brands/**",  "/products/**", "/promos/**", "/bills/**").hasAnyRole(RoleEnum.ADMIN.name());
                    req.requestMatchers(HttpMethod.PUT, "/brands/**",  "/products/**", "/promos/**", "/bills/**").hasAnyRole(RoleEnum.ADMIN.name());
                    req.anyRequest().denyAll();
                     }
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(new JwtTokenValidatorFilter(jwtUtils), BasicAuthenticationFilter.class)
                .exceptionHandling(e ->
                    e.authenticationEntryPoint(accessDeniedHandler)
                )
                .build();
    }
}
