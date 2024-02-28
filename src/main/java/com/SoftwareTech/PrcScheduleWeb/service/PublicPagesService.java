package com.SoftwareTech.PrcScheduleWeb.service;

import com.SoftwareTech.PrcScheduleWeb.auth.JwtService;
import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PublicPagesService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDetailsService userDetailsService;

    public ModelAndView renderLoginPage(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        final String authTokenFromCookies = jwtService.getAccessTokenInCookies(request);
        if (authTokenFromCookies != null) {
            final String instituteEmail = jwtService.getInstituteEmail(authTokenFromCookies);
            if (instituteEmail != null) {
                Account userDetailsFromDB = (Account) userDetailsService.loadUserByUsername(instituteEmail);
                if (jwtService.isValidToken(authTokenFromCookies, userDetailsFromDB)) {
                    response.sendRedirect(String.format(
                        "%s/%s/home",
                        request.getContextPath(),
                        userDetailsFromDB.getRole().toString().toLowerCase()
                    ));
                    return null;
                }
            }
        }
        return staticUtilMethods.customizeModelAndView(request, "login");
    }
}
