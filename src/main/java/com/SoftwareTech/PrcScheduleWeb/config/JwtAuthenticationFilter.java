package com.SoftwareTech.PrcScheduleWeb.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtInputToken;
        final String instituteEmail;

        //--Check if JWT Token doesn't exist in Headers.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //--Get JWT Token.
        jwtInputToken = authHeader.substring(7);
        instituteEmail = jwtService.getInstituteEmail(jwtInputToken);

        if (instituteEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //--Get UserDetails information (email, pass, roles,...)
            UserDetails userDetailsFromDB = this.userDetailsService.loadUserByUsername(instituteEmail);

            //--If Token is invalid, we don't have to validate UserDetails.
            boolean isValid = jwtService.isValidToken(jwtInputToken, userDetailsFromDB);
            if (isValid) {
                //--UsernamePasswordAuthenticationToken Class represents for Authentication Process.
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetailsFromDB,
                    null,
                    userDetailsFromDB.getAuthorities()
                );
                authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
