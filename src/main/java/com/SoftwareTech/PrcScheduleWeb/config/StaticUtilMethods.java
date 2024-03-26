package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.service.AuthService.JwtService;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
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
    public ModelAndView customResponseModelView(@NonNull HttpServletRequest request, String model) {
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

    /**Spring MVC: Get the "page" param in HttpServletRequest, customize and return as PageRequest Object.**/
    public PageRequest getPageRequest(HttpServletRequest request) {
        //--Minimum the page index of Website interface is 1.
        int requestPage = 1;

        //--Compare Maximum with 1 if there's a negative(-) page number.
        try { requestPage = Math.max(Integer.parseInt(request.getParameter("page")), 1); }
        catch (NumberFormatException ignored) {}

        /*
          - Minimum the "pageNumber" in "PagingAndSorting" is 0.
          [?] Why don't I put the 0 at the beginning (line-59,62)?
          => Reason: Minimum of "request.getParameter("page")" (line-62) is always 1.
          => So: This will work with a "null" param, and wrong with '1' default minimum page number.
         */
        return PageRequest.of(requestPage - 1, 10);
    }
}
