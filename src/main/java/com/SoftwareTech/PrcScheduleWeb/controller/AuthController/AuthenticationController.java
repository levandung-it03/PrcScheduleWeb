package com.SoftwareTech.PrcScheduleWeb.controller.AuthController;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoAuthenticationResponse;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoAuthentication;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.ManagerRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import com.SoftwareTech.PrcScheduleWeb.service.AuthService.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

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
    private final ManagerRepository managerRepository;
    @Autowired
    private final TeacherRepository teacherRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<DtoAuthenticationResponse> register(@RequestBody DtoRegisterAccount request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(request));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(
        @Valid @ModelAttribute("authObject") DtoAuthentication authObject,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorCode", bindingResult.getFieldErrors().getFirst());
            return "redirect:/public/login";
        }

        try {
            //--Authenticate Username-Password and return JWT Token.
            DtoAuthenticationResponse authResult = authService.authenticate(authObject);

            //--Create Cookie with JWT AccessToken.
            Cookie accessTokenCookie = authService.customizeAcessTokenToServeCookie(authResult.getToken());

            //--Send AccessToken to Cookie storage.
            response.addCookie(accessTokenCookie);

            //--Get redirecting URL to Home: "classpath:/role/home".
            String lowerCaseRole = authResult.getRole().toString().toLowerCase();
            return String.format("redirect:/%s/sub-page/%s/set-%s-info", lowerCaseRole, lowerCaseRole, lowerCaseRole);
        } catch (UsernameNotFoundException ignored) {
            //--Will be ignored because of security.
            redirectAttributes.addFlashAttribute("errorCode", "error_account_01");
        } catch (BadCredentialsException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_account_03");
        }
        return "redirect:/public/login";
    }

/*
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(@RequestBody DtoAuthentication authObject, HttpServletResponse response) {
        try {
            DtoAuthenticationResponse authResult = authService.authenticate(authObject);
            Cookie accessTokenCookie = authService.customizeAccessTokenToServeCookie(authResult.token());
            response.addCookie(accessTokenCookie);
            return authResult.token();
        } catch (UsernameNotFoundException ignored) {
            return "Error Username";
        } catch (BadCredentialsException ignored) {
            return "Error Password";
        }
    }
 */
}
