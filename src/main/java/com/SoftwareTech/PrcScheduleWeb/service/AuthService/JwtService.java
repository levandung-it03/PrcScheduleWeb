package com.SoftwareTech.PrcScheduleWeb.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    /**SECRET_KEY will serve the Signing progress**/
    @Value("${secret.key}")
    private String SECRET_KEY;

    /**JwtServices: Generating Token Methods by Custom Claims**/
    public String generateToken(Map<String, Object> customClaims, UserDetails userDetails) {
        return Jwts.builder()
            .claims(customClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+ 2*60*60*1000))
            .signWith(this.getSigningKey())
            .compact();
    }

    /**JwtServices: Generating Token Methods by Registered Claims**/
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(
            new HashMap<>(),    //--Just let CustomClaims is empty.
            userDetails
        );
    }

    /**JwtServices: Verifying Token if it isn't expired**/
    public boolean isExpiredToken(String token) {
        Date expirationDate = extractTokenAndGetAllClaims(token).getExpiration();
        return !expirationDate.after(new Date(System.currentTimeMillis()));
    }

    /**JwtServices: Verifying Token if it's a valid Token (created by Spring App)**/
    public boolean isValidToken(String token, UserDetails userDetails) {
        //--Will validate more Claims in here, but we use RegisteredClaim.
        final String originInstituteEmail = extractTokenAndGetAllClaims(token).getSubject();
        return userDetails.getUsername().equals(originInstituteEmail) && !isExpiredToken(token);
    }

    /**JwtServices: Extract Token to get Claims**/
    public String getInstituteEmail(String token) {
        return this.parseTokenAndGetClaim(token, Claims::getSubject);
    }

    /**JwtSupportingServices: Extracting Token Methods**/
    public <T> T parseTokenAndGetClaim(String token, Function<Claims, T> claimsResolverAsCallback) {
        Claims claims = this.extractTokenAndGetAllClaims(token);
        return claimsResolverAsCallback.apply(claims);
    }

    /**JwtSupportingServices: Extracting Token Methods**/
    public Claims extractTokenAndGetAllClaims(String token) {
        return Jwts.parser()            //--Getting "JwtParserBuilder" object to configure.
            .verifyWith(getSigningKey())//--Config "JwtParserBuilder" object with SIGNING_KEY.
            .build()                    //--Build "JwtParser" from configured "JwtParserBuilder".
            .parseSignedClaims(token)   //--Verify SIGNING_KEY of input Token, and extract it.
            .getPayload();              //--Return "Claims" object, contains all Claims (Payload).
    }

    /**
     * Get SIGNING_KEY (from SECRET_KEY as JWT Technique) as PublicKey.
     * <br>This SIGNING_KEY used to Generate or Parse (Verify - Extract) Token.
     * **/
    private SecretKeySpec getSigningKey() {
        //--Decode SECRET_KEY (generated by BASE64 Algorithm) to byte[].
        byte[] keyAsBytes = Decoders.BASE64.decode(SECRET_KEY);

        //--Generate SIGNING_KEY by H-MAC Algorithm with SECRET_KEY (as byte[]).
        return new SecretKeySpec(keyAsBytes, "HmacSHA256");
    }

    /**JwtServices: Get the EncodedAccessToken as a Cookie in Cookies**/
    public String getAccessTokenInCookies(HttpServletRequest request) {
        try {
            Optional<Cookie> authCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("AccessToken"))
                .findFirst();

            if (authCookie.isEmpty() || authCookie.orElseThrow().getMaxAge() == 0)
                return null;

            return new String(Base64.getDecoder().decode(authCookie.orElseThrow().getValue()));
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Spring Security: Clear all Cookies and kill all JWT Tokens.
     **/
    public void clearAllTokenCookies(HttpServletRequest request, HttpServletResponse response) {
        //--Clear cookies from client device.
        Arrays.stream(request.getCookies()).forEach(cookie -> {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        });
    }

    /*
     * Help us generate a random SECRET_KEY.
     * private String getRandomSecretKey() {
     *    try {return Base64.getEncoder().encodeToString(KeyGenerator.getInstance("HmacSHA256").generateKey().getEncoded());
     *    } catch (NoSuchAlgorithmException e) {throw new RuntimeException();}
     * }
     * */
}
