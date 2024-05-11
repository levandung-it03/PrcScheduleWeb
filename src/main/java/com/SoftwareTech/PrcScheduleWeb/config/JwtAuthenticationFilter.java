package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.service.AuthService.JwtService;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

    @Value("${url.post.auth.prefix.v1}")
    private String authPrefix;

    /**
     * Customized JWT Structure with ServerSideRendering:
     * <br> 1. Check Bypass Urls.
     * <br>
     * <br> 2. Check Existing Token.
     * <br> - In Headers: with frame "Bearer [AccessToken]".
     * <br> - In Cookies: with frame "Cookie(name="AccessToken", value=[Bass64_Encoded_AccessToken])".
     * <br> ==> Not found: "Redirect:/public/login".
     * <br> ==> Cookie Expired: "Redirect:/public/login".
     * <br>
     * <br> 3. Check Token whether it's valid.
     * <br> ==> Invalid Token: Clear all Cookies.
     */
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String accessTokenInCookies = jwtService.getAccessTokenInCookies(request);

        TestingUrlsClass.detectAllUrlWithRequest(request);

        if (isBypassToken(request)) {
            //--Find NextFilter or NextHttpSecurityStep if it's Bypassed.
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtInputToken;
        if (accessTokenInCookies != null) {
            //--Get JWT Token if there's a Cookies.AccessToken.
            jwtInputToken = accessTokenInCookies;
        } else {
            //--Can't Bypass and the JWT Token doesn't exist in both Headers & Cookie.
            response.sendRedirect("/public/login");
            return;
        }

        final String instituteEmail = jwtService.getInstituteEmail(jwtInputToken);
        if (instituteEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //--Get UserDetails information (email, pass, roles,...)
            UserDetails userDetailsFromDB = userDetailsService.loadUserByUsername(instituteEmail);

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
                //--Set the Authenticated User into Security Context of Spring App to use in everywhere.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            //--Clear all invalid Token inside Cookies.
            else if (request.getCookies() != null) {
                jwtService.clearAllTokenCookies(request, response);
                response.sendRedirect("/public/login");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final String requestURL = request.getServletPath();
        final String requestMethod = request.getMethod();

        boolean isStaticResourcesRequest = requestURL.startsWith("/public/")
            || requestURL.startsWith("/js/")
            || requestURL.startsWith("/css/")
            || requestURL.startsWith("/img/")
            || requestURL.startsWith("/favicon.ico");
        if (isStaticResourcesRequest || request.getDispatcherType() == DispatcherType.FORWARD)
            return true;

        final Map<String, String> bypassTokens = new HashMap<>();
        bypassTokens.put(String.format("%s/register", this.authPrefix), "POST");
        bypassTokens.put(String.format("%s/authenticate", this.authPrefix), "POST");
        bypassTokens.put(String.format("%s/change-password/otp-for-new-password", this.authPrefix), "POST");

        if (bypassTokens.containsKey(requestURL))
            return bypassTokens.get(requestURL).contains(requestMethod);

        return false;
    }

}
