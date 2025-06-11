package com.example.demo.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

//Le check de cookie manuel peut etre remplacé par le OncePerRequestfilter
@Service
public class JwtAuthentificationService
{

    @Value("${jwt.expires_in}")
    private Integer EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Value("${jwt.secret}")
    //Le token est déjà injecté depuis application.properties via @Value. Ne pas le mettre en dur dans le code
    private String JWT_SECRET;
    
    private SecretKey getSecuredKey() // SecretKey recommandé pour la nouvelle version de .signWith(SecretKey)
    {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }
    
    public ResponseCookie generateToken(String username) {
        String jwt = Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN)).signWith(getSecuredKey()).compact(); // signature avec la clef encryptée
        return ResponseCookie.from(TOKEN_COOKIE, jwt).httpOnly(true)  //utilisateur du bon nom de cookie
                .maxAge(EXPIRES_IN * 1000).path("/").build();
    }

    public String getSubject(String token) {
       return Jwts.parser().setSigningKey(getSecuredKey()).parseClaimsJws(token).getBody().getSubject();
    };

    public String getUsernameFromCookie(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {

            String token = Stream.of(cookies)
                    .filter(cookie -> cookie.getName().equals(TOKEN_COOKIE)) // utilisation du bon nom de cookie
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
                    .setSigningKey(getSecuredKey())
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
        return false; // doit retourner false en cas d'erreur
    }
    
   
}
