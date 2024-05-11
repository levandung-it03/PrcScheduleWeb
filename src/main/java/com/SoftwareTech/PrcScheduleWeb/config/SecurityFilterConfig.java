package com.SoftwareTech.PrcScheduleWeb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE + 1)
public class SecurityFilterConfig {
    @Autowired
    private final AuthenticationProvider authenticationProvider;
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**SecurityFilterChain object does both Authentication and Authorization.**/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
        //--Custom OncePerRequestFilter always run before the Authentication.
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request ->
                request
                    .requestMatchers(GET, "/public/**").permitAll()
                    .requestMatchers(GET, "/home").hasAnyAuthority("MANAGER", "TEACHER")
                    .requestMatchers(GET, "/teacher/**").hasAuthority("TEACHER")
                    .requestMatchers(GET, "/manager/**").hasAuthority("MANAGER")

                    .requestMatchers(POST, "/service/v1/auth/**").permitAll()
                    .requestMatchers(POST, "/service/v1/teacher/**").hasAuthority("TEACHER")
                    .requestMatchers(POST, "/service/v1/manager/**").hasAuthority("MANAGER")

                    .anyRequest().permitAll()
            )
        //--Custom Session to not store Session because of JWT.
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
        //--Configure how Spring Security will authenticate Account.
        //--This AuthProvider be used by AuthenticationManager.
            .authenticationProvider(authenticationProvider);
        return httpSecurity.build();
    }

}
