package com.example.quizapplication.security;

import com.example.quizapplication.utils.RestConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.quizapplication.entity.role.Permission.*;
import static com.example.quizapplication.entity.role.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(RestConstants.OPEN_PAGES)
                        .permitAll()
                        .requestMatchers("/api/v1/**").hasAnyRole(ADMIN.name())
                        .requestMatchers(GET, "/api/v1/**").hasAnyAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/**").hasAnyAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/**").hasAnyAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/**").hasAnyAuthority(ADMIN_DELETE.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();


    }

}
