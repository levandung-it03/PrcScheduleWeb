package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.model.Manager;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.ManagerRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import com.SoftwareTech.PrcScheduleWeb.service.AuthService.JwtService;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
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
    @Autowired
    private final ManagerRepository managerRepository;
    @Autowired
    private final TeacherRepository teacherRepository;

    /**Spring MVC: Customize returned ModelAndView to show ErrMessage or SucceedMessages.**/
    public ModelAndView customResponseModelView(
        @NonNull HttpServletRequest request,
        @NonNull Map<String, Object> model,
        String pageModel
    ) {
        ModelAndView modelAndView = new ModelAndView(pageModel);

        Object errCode = model.getOrDefault("errorCode", null);
        if (errCode == null)    errCode = request.getSession().getAttribute("errorCode");
        if (errCode != null) {
            modelAndView.addObject("errorMessage", responseMessages.get(errCode.toString()));
            request.getSession().invalidate();
        }

        Object succeedCode = model.getOrDefault("succeedCode", null);
        if (succeedCode == null)    succeedCode = request.getSession().getAttribute("succeedCode");
        if (succeedCode != null) {
            modelAndView.addObject("succeedMessage", responseMessages.get(succeedCode.toString()));
            request.getSession().invalidate();
        }

        //--Prepare data for Header of Pages.
        Account account = this.getAccountInfoInCookie(request);
        if (account != null) {
            if (account.getRole() == Role.MANAGER) {
                Manager user = managerRepository.findByAccountAccountId(account.getAccountId());
                modelAndView.addObject("user", user);
                modelAndView.addObject("id", user.getManagerId());
            } else if (account.getRole() == Role.TEACHER) {
                Teacher user = teacherRepository.findByAccountAccountId(account.getAccountId());
                modelAndView.addObject("user", user);
                modelAndView.addObject("id", user.getTeacherId());
            }
        }

        return modelAndView;
    }

    /**Spring MVC and Spring Security: Customize redirected HomePage when taking an unauthorized request with Cookies.**/
    public Account getAccountInfoInCookie(HttpServletRequest request) {
        final String authTokenFromCookies = jwtService.getAccessTokenInCookies(request);
        if (authTokenFromCookies != null) {
            final String instituteEmail = jwtService.getInstituteEmail(authTokenFromCookies);
            if (instituteEmail != null) {
                Account userDetailsFromDB = (Account) userDetailsService.loadUserByUsername(instituteEmail);
                if (jwtService.isValidToken(authTokenFromCookies, userDetailsFromDB)) {
                    return userDetailsFromDB;
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

    /**Spring Security: Clear all Cookies and kill all JWT Tokens.**/
    public void clearAllTokenCookies(HttpServletRequest request, HttpServletResponse response) {
        Arrays.stream(request.getCookies()).forEach(cookie -> {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        );
    }
}
