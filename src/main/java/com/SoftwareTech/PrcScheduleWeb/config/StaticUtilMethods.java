package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.model.Manager;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.ManagerRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import com.SoftwareTech.PrcScheduleWeb.service.AuthService.JwtService;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import jakarta.servlet.http.Cookie;
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

import java.security.SecureRandom;
import java.util.*;

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
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Spring MVC: Customize returned ModelAndView to show ErrMessage or SucceedMessages.
     **/
    public ModelAndView customResponsiveModelView(
        @NonNull HttpServletRequest request,
        @NonNull Model model,
        String pageModel
    ) {
        ModelAndView modelAndView = new ModelAndView(pageModel);
        modelAndView.addObject("errorMessage", this.getMessageFromCode("errorCode", request, model));
        modelAndView.addObject("succeedMessage", this.getMessageFromCode("succeedCode", request, model));
        return modelAndView;
    }
    private String getMessageFromCode(String codeType, HttpServletRequest request, Model model) {
        Map<String, Object> fieldValuePairsInModel = model.asMap();
        Object messageCode = fieldValuePairsInModel.getOrDefault(codeType, null);
        if (messageCode == null) {
            messageCode = request.getSession().getAttribute(codeType);
            request.getSession().removeAttribute(codeType);
        }
        if (messageCode != null) {
            return responseMessages.getOrDefault(messageCode.toString(), messageCode.toString());
        }
        return null;
    }

    /**
     * Spring MVC: Customize returned ModelAndView to show data of header.
     **/
    public ModelAndView insertingHeaderDataOfModelView(
        @NonNull HttpServletRequest request,
        @NonNull ModelAndView modelAndView
    ) {
        //--Prepare data for Header of Pages.
        Account account = this.getAccountInfoInCookie(request);
        if (account != null) {
            try {
                if (account.getRole() == Role.MANAGER) {
                    Manager person = managerRepository
                        .findByAccountAccountId(account.getAccountId())
                        .orElseThrow(() -> new NoSuchElementException("Manager Id not found"));
                    modelAndView.addObject("person", person);
                    modelAndView.addObject("role", "manager");
                } else if (account.getRole() == Role.TEACHER) {
                    Teacher person = teacherRepository
                        .findByAccountAccountId(account.getAccountId())
                        .orElseThrow(() -> new NoSuchElementException("Teacher Id not found"));
                    modelAndView.addObject("person", person);
                    modelAndView.addObject("role", "teacher");
                }
            } catch (NoSuchElementException ignored) {
                //--Redirect to setting person-info page when account is has empty-person-info.
                String lowerCaseRole = account.getRole().toString().toLowerCase();
                return new ModelAndView(
                    String.format("redirect:/%s/sub-page/%s/set-%s-info", lowerCaseRole, lowerCaseRole, lowerCaseRole)
                );
            }
        }
        return modelAndView;
    }

    /**
     * Spring MVC and Spring Security: Customize redirected HomePage when taking an unauthorized request with Cookies.
     **/
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

    /**
     * Spring MVC: Get the "page" param in HttpServletRequest, customize and return as PageRequest Object.
     **/
    public PageRequest getPageRequest(HttpServletRequest request) {
        //--Minimum the page index of Website interface is 1.
        int requestPage = 1;

        //--Compare Maximum with 1 if there's a negative(-) page number.
        try {
            requestPage = Math.max(Integer.parseInt(request.getParameter("page")), 1);
        } catch (NumberFormatException ignored) {
        }

        /*
          - Minimum the "pageNumber" in "PagingAndSorting" is 0.
          [?] Why don't I put the 0 at the beginning (line-59,62)?
          => Reason: Minimum of "request.getParameter("page")" (line-62) is always 1.
          => So: This will work with a "null" param, and wrong with '1' default minimum page number.
         */
        return PageRequest.of(requestPage - 1, 10);
    }

    /**
     * Spring Security: Get the random OTP code with (length=6, chars=[A-Z, 0-9]).
     **/
    public String generateOTP(int length) {
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }
}
