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

/**These are the controllers that don't need to Authorize**/
@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/service/v1/auth")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<DtoAuthenticationResponse> register(@RequestBody DtoRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(request));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ModelAttribute("authObject")
    public void authenticate(DtoAuthentication authObject, HttpServletResponse res, HttpServletRequest req
    ) throws IOException {
        try {
            DtoAuthenticationResponse authResult = authService.authenticate(authObject);
            final String homeUrl = req.getContextPath() + "/" + authResult.role().toString().toLowerCase() + "/home";
            //--Send AccessToken to Cookie storage.
            String encodedToken = Base64.getEncoder().encodeToString(authResult.token().getBytes());
            Cookie accessTokenCookie = new Cookie("AccessToken", encodedToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(true);
            accessTokenCookie.setPath("/");
            res.addCookie(accessTokenCookie);

            res.sendRedirect(homeUrl);
        } catch (UsernameNotFoundException ignored) {
            //--Will be ignored because of security.
            res.sendRedirect(req.getContextPath() + "/public/login?errorMessage=eMv1at01");
        } catch (BadCredentialsException ignored) {
            res.sendRedirect(req.getContextPath() + "/public/login?errorMessage=eMv1at02");
        }
    }
}
