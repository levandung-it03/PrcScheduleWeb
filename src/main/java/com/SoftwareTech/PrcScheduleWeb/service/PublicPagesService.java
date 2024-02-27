package com.SoftwareTech.PrcScheduleWeb.service;

import com.SoftwareTech.PrcScheduleWeb.auth.JwtService;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PublicPagesService {
    @Autowired
    private final Map<String, String> errMessages;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDetailsService userDetailsService;

    public ModelAndView renderLoginPage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String authTokenFromCookies = jwtService.getAccessTokenInCookies(request);
        if (authTokenFromCookies != null) {
            final String instituteEmail = jwtService.getInstituteEmail(authTokenFromCookies);
            if (instituteEmail != null) {
                Account userDetailsFromDB = (Account) userDetailsService.loadUserByUsername(instituteEmail);
                if (jwtService.isValidToken(authTokenFromCookies, userDetailsFromDB)) {
                    String matchedHomeUrlWithRole = String.format(
                        "%s/%s/home",
                        request.getContextPath(),
                        userDetailsFromDB.getRole().toString().toLowerCase()
                    );
                    response.sendRedirect(matchedHomeUrlWithRole);
                    return null;
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("login");
        String errCode = request.getParameter("errorMessage");
        String errMess = (errCode == null) ? "none" : errMessages.get(errCode);

        modelAndView.addObject("errorMessage", errMess);
        return modelAndView;
    }
}
