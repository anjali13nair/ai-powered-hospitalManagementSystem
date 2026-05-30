package com.github.anjalin.hospitalManagement.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static com.github.anjalin.hospitalManagement.entity.type.PermissionType.*;
import static com.github.anjalin.hospitalManagement.entity.type.RoleType.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/auth/**", "/departments/public").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/admin/**")
                            .hasAnyAuthority(APPOINTMENT_DELETE.getPermission(),
                                USER_MANAGE.getPermission())
                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                        .requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(), ADMIN.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2 -> oAuth2
                        .failureHandler((request, response, exception) -> {
                            log.error("OAuth2 error: {}", exception.getMessage());
                            handlerExceptionResolver.resolveException(request, response, null, exception);
                        })
                        .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .authenticationEntryPoint((request, response, authException) -> {
                                    handlerExceptionResolver.resolveException(request, response, null, authException);
                                })
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
                                }));

//                .formLogin();
        return httpSecurity.build();
    }

}
