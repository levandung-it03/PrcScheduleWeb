package com.SoftwareTech.PrcScheduleWeb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Autowired
    private final AuthenticationProvider authenticationProvider;
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**SecurityFilterChain object does both Authentication and Authorization.**/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                //--ROLE: Unknown, Permit Non-authentication pages like Sign-up, Log-in,...
                    .requestMatchers("")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                //--Has more than 1 ROLE: .requestMatchers("/admin/**")....hasRole("ADMIN").
                //--Using .hasRole("USER") with multiple ROLES Authentication instead of .authenticated().
  		 	)
            .sessionManagement((sessionManagement) ->
                sessionManagement
                //--Doesn't have a Session for public request.
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
