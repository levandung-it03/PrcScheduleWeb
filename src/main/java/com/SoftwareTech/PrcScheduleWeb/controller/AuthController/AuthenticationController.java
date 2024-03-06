package com.SoftwareTech.PrcScheduleWeb.controller.AuthController;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoAuthenticationResponse;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoAuthentication;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.service.AuthService.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**These are the controllers that don't need to Authorize**/
@RequiredArgsConstructor
@Controller
@RequestMapping(path = "${url.post.auth.prefix.v1}")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<DtoAuthenticationResponse> register(@RequestBody DtoRegisterAccount request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(request));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ModelAttribute("authObject")
    public void authenticate(DtoAuthentication authObject, HttpServletResponse response, HttpServletRequest request
    ) throws IOException {
        try {
            //--Authenticate Username-Password and return JWT Token.
            DtoAuthenticationResponse authResult = authService.authenticate(authObject);

            //--Create Cookie with JWT AccessToken.
            Cookie accessTokenCookie = authService.custmoizeAcessTokenToServeCookie(authResult.getToken());

            //--Send AccessToken to Cookie storage.
            response.addCookie(accessTokenCookie);

            //--Get redirecting URL to Home: "classpath:/role/home".
            response.sendRedirect("/home");
        } catch (UsernameNotFoundException ignored) {
            //--Will be ignored because of security.
            response.sendRedirect(request.getContextPath() + "/public/login?errorMessage=eMv1at01");
        } catch (BadCredentialsException ignored) {
            response.sendRedirect(request.getContextPath() + "/public/login?errorMessage=eMv1at02");
        }
    }

/*
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(@RequestBody DtoAuthentication authObject, HttpServletResponse response) {
        try {
            DtoAuthenticationResponse authResult = authService.authenticate(authObject);
            Cookie accessTokenCookie = authService.custmoizeAcessTokenToServeCookie(authResult.token());
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