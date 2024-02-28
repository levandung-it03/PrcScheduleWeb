package com.SoftwareTech.PrcScheduleWeb.auth;
import com.SoftwareTech.PrcScheduleWeb.dto.DtoAuthentication;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

/**These are the controllers that don't need to Authorize**/
@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/service/v1/auth")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authService;

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ResponseEntity<DtoAuthenticationResponse> register(@RequestBody DtoRegisterRequest request) {
//        return ResponseEntity.status(HttpStatus.OK).body(authService.register(request));
//    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ModelAttribute("authObject")
    public void authenticate(DtoAuthentication authObject, HttpServletResponse response, HttpServletRequest request
    ) throws IOException {
        try {
            //--Authenticate Username-Password and return JWT Token.
            DtoAuthenticationResponse authResult = authService.authenticate(authObject);

            //--Create Cookie with JWT AccessToken.
            Cookie accessTokenCookie = authService.custmoizeAcessTokenToServeCookie(authResult.token());

            //--Send AccessToken to Cookie storage.
            response.addCookie(accessTokenCookie);

            //--Get redirecting URL to Home: "classpath:/role/home".
            String matchedHomeUrlWithRole = String.format(
                "%s/%s/home",
                request.getContextPath(),
                authResult.role().toString().toLowerCase()
            );
            response.sendRedirect(matchedHomeUrlWithRole);
        } catch (UsernameNotFoundException ignored) {
            //--Will be ignored because of security.
            response.sendRedirect(request.getContextPath() + "/public/login?errorMessage=eMv1at01");
        } catch (BadCredentialsException ignored) {
            response.sendRedirect(request.getContextPath() + "/public/login?errorMessage=eMv1at02");
        }
    }
}
