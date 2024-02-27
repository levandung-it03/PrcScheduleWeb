package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.auth.JwtService;
import com.SoftwareTech.PrcScheduleWeb.auth.TestClass;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
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
        final String jwtInputToken;
        final String instituteEmail;
        final String authHeader = request.getHeader("Authorization");
        final Optional<Cookie> authCookie = (request.getCookies() != null)
            ? Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("AccessToken"))
                .findFirst()
            : Optional.empty();

        //--Check if the request is Bypassed.
        if (isBypassToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        //--Debug Important Point.
        // TestClass.detectAllUrlWithRequest(request);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //--Get JWT Token if there's a Headers.Authorization.
            jwtInputToken = authHeader.substring(7);
        } else if (authCookie.isPresent()) {
            //--Get JWT Token if there's a Cookies.AccessToken.
            jwtInputToken = new String(Base64.getDecoder().decode(authCookie.orElseThrow().getValue()));
        } else {
            /*--Redirect LoginPage when we don't have Headers.Authorization or Cookies.AccessToken:
                1. Not Bypass.
                2. JWT Token doesn't exist in both Headers & Cookie.
            */
            response.sendRedirect("/public/login");
            filterChain.doFilter(request, response);
            return;
        }

        instituteEmail = jwtService.getInstituteEmail(jwtInputToken);
        if (instituteEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //--Get UserDetails information (email, pass, roles,...)
            UserDetails userDetailsFromDB = this.userDetailsService.loadUserByUsername(instituteEmail);

            //--If Token is expired, let user logins (will be ignored, because Cookie has smaller age than Token).
            if (jwtService.isExpiredToken(jwtInputToken)) {
                filterChain.doFilter(request, response);
                response.sendRedirect("/public/login");
                return;
            }

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
            || requestURL.startsWith("/img/")
            || requestURL.startsWith("/favicon.ico");
        if (isStaticResourcesRequest || request.getDispatcherType() == DispatcherType.FORWARD)
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
