package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDetailsService userDetailsService;

    @Value("${url.post.auth.prefix.v1}")
    private String authPrefix;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final Optional<Cookie> authCookie = request.getCookies() != null
            ? Arrays.stream(request.getCookies()).filter(cookie ->
            cookie.getName().equals("AccessToken")
        ).findAny() : Optional.empty();
        final String jwtInputToken;
        final String instituteEmail;

        //--Check if the request is Bypassed.
        if (isBypassToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        //--Check if JWT Token doesn't exist in both Headers & Cookie.
        if (authCookie.isEmpty() && (authHeader == null || !authHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }

        //--Get JWT Token.
        if (authHeader != null)
            jwtInputToken = authHeader.substring(7);
        else {
            String encodedToken = authCookie.orElseThrow(() -> new AuthenticationException("Auth Token is missing!") {
                //--Anonymous Object with Abstract Class.
                //--If Cookie has value but not JWT Token (any request must be attached with JWT Token).
            }).getValue();
            jwtInputToken = new String(Base64.getDecoder().decode(encodedToken));
        }

        instituteEmail = jwtService.getInstituteEmail(jwtInputToken);
        if (instituteEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //--Get UserDetails information (email, pass, roles,...)
            UserDetails userDetailsFromDB = this.userDetailsService.loadUserByUsername(instituteEmail);

            //--If Token is invalid, we don't have to validate UserDetails.
            if (jwtService.isValidToken(jwtInputToken, userDetailsFromDB)) {
                //--UsernamePasswordAuthenticationToken Class represents for Authentication Process.
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetailsFromDB,
                    null,   //--Password is null, because we already have JWT Token.
                    userDetailsFromDB.getAuthorities()
                );
                //--Put into AuthToken the more authenticated details like IP-Address, User-Agent,...
                authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //--Tell Spring that Authentication was passed (Token is valid -> this is user) in the whole Context.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final String requestURL = request.getServletPath();
        final String requestMethod = request.getMethod();

        boolean isStaticResourcesRequest = requestURL.startsWith("/js/")
            || requestURL.startsWith("/css/")
            || requestURL.startsWith("/img/");
        if (isStaticResourcesRequest)
            return true;

        final Map<String, String> bypassTokens = new HashMap<>();
        bypassTokens.put("/public/login", "GET");
        bypassTokens.put(String.format("%s/register", this.authPrefix), "POST");
        bypassTokens.put(String.format("%s/authenticate", this.authPrefix), "POST");

        if (bypassTokens.containsKey(requestURL))
            return bypassTokens.get(requestURL).contains(requestMethod);

        return false;
    }
}