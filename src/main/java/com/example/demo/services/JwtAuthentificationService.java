package com.example.demo.services;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Stream;

@Service
public class JwtAuthentificationService {

    @Value("${jwt.expires_in}")
    private Integer EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Value("${jwt.secret}")
    private final String JWT_SECRET = "monSecretANePasDivulguer";

    public ResponseCookie generateToken(String username) {
        String jwt = Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN)).signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
        return ResponseCookie.from("TOKEN_COOKIE", jwt).httpOnly(true)
                .maxAge(EXPIRES_IN).path("/").build();
    }

    public String getSubject(String token) {
       return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    };

    public String getUsernameFromCookie(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {

            String token = Stream.of(cookies)
                    .filter(cookie -> cookie.getName().equals(TOKEN_COOKIE))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

            if (token != null) {
                return getSubject(token);

            }
        }
        throw new Exception("Nothing found with cookie");
    }

    public Boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expiré");
        } catch (UnsupportedJwtException e) {
            System.out.println("Format du token non supporté");
        } catch (MalformedJwtException e) {
            System.out.println("Token malformé");
        } catch (SignatureException e) {
            System.out.println("Signature invalide");
        } catch (IllegalArgumentException e) {
            System.out.println("Token vide ou null");
        }catch (Exception e) {
            System.out.println("C'est pas normal ça....");
        }
        return true;
    }

}
