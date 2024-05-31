package com.SoftwareTech.PrcScheduleWeb.controller.AuthController;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoAuthenticationResponse;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoAuthentication;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoChangePassword;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoAddTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.service.AuthService.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * These are the controllers that don't need to Authorize
 **/
@RequiredArgsConstructor
@Controller
@RequestMapping(path = "${url.post.auth.prefix.v1}")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authService;
    @Autowired
    private final Validator hibernateValidator;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<DtoAuthenticationResponse> register(@RequestBody ReqDtoAddTeacherAccount request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(request));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(
        @ModelAttribute("authObject") DtoAuthentication authObject,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) {
        Set<ConstraintViolation<DtoAuthentication>> violations = hibernateValidator.validate(authObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:/public/login";
        }

        try {
            //--Authenticate Username-Password and return JWT Token.
            DtoAuthenticationResponse authResult = authService.authenticate(authObject);

            //--Create Cookie with JWT AccessToken.
            Cookie accessTokenCookie = authService.customizeAccessTokenToServeCookie(authResult.getToken());

            //--Send AccessToken to Cookie storage.
            response.addCookie(accessTokenCookie);

            //--Get redirecting URL to Home: "classpath:/role/home".
            return "redirect:/home";
        } catch (UsernameNotFoundException ignored) {
            //--Will be ignored because of security.
            redirectAttributes.addFlashAttribute("errorCode", "error_account_01");
        } catch (BadCredentialsException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_account_03");
        }
        return "redirect:/public/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        authService.logout(request, response);
        //--Remove the authenticated user which is being stored in system-app (to validate access-token).
        request.logout();
        return "redirect:/public/login";
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public String changePassword(
        @ModelAttribute("changePasswordObj") DtoChangePassword changePasswordObj,
         HttpServletRequest request,
         HttpServletResponse response,
         RedirectAttributes redirectAttributes
    ) {
        Set<ConstraintViolation<DtoChangePassword>> violations = hibernateValidator.validate(changePasswordObj);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:/public/login";
        }

        try {
            authService.changePassword(request, response, changePasswordObj);
            return "redirect:/public/login";
        } catch (IllegalArgumentException | NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_03");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/change-password/get-otp", method = RequestMethod.POST)
    public String getOtpToChangePassword(
        @ModelAttribute("changePasswordObj") DtoChangePassword changePasswordObj,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        try {
            authService.sendingOtpCode(request, changePasswordObj.getInstituteEmail());
            //--Sending information back to this page.
            redirectAttributes.addFlashAttribute("changePasswordObj", changePasswordObj);
            return "redirect:" + standingUrl;
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_03");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:/home";
    }


    @RequestMapping(value = "/change-password/otp-for-new-password", method = RequestMethod.POST)
    public String getOtpForNewPassword(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        final String standingUrl = request.getHeader("Referer");
        try {
            authService.sendingOtpCodeForNewPassword(request);
            redirectAttributes.addFlashAttribute("succeedCode", "error_account_04");
            return "redirect:/public/login";
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_account_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
}
