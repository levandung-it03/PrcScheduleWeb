package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.service.AuthService.JwtService;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StaticUtilMethods {
    @Autowired
    private final Map<String, String> responseMessages;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDetailsService userDetailsService;

    /**Spring MVC: Customize returned ModelAndView to show ErrMessage or SucceedMessages.**/
    public ModelAndView customizeResponsiveModelAndView(@NonNull HttpServletRequest request, String model) {
        String errCode = request.getParameter("errorMessage");
        String errMess = (errCode == null) ? "none" : responseMessages.get(errCode);

        String succeedCode = request.getParameter("succeedMessage");
        String succeedMess = (succeedCode == null) ? "none" : responseMessages.get(succeedCode);

        ModelAndView modelAndView = new ModelAndView(model);
        modelAndView.addObject("errorMessage", errMess);
        modelAndView.addObject("succeedMessage", succeedMess);

        return modelAndView;
    }

    /**Spring MVC and Spring Security: Customize redirected HomePage when taking an unauthorized request with Cookies.**/
    public String isAValidAccessTokenInCookies(HttpServletRequest request) {
        final String authTokenFromCookies = jwtService.getAccessTokenInCookies(request);
        if (authTokenFromCookies != null) {
            final String instituteEmail = jwtService.getInstituteEmail(authTokenFromCookies);
            if (instituteEmail != null) {
                Account userDetailsFromDB = (Account) userDetailsService.loadUserByUsername(instituteEmail);
                if (jwtService.isValidToken(authTokenFromCookies, userDetailsFromDB)) {
                    return userDetailsFromDB.getRole().toString().toLowerCase();
                }
            }
        }
        return null;
    }
}
