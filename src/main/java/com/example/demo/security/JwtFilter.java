package com.example.demo.security;

import com.example.demo.models.UserApp;
import com.example.demo.repositories.UserAppRepository;
import com.example.demo.services.JwtAuthentificationService;
import com.example.demo.services.UserAppService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private JwtAuthentificationService jwtAuthentificationService;



    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Autowired
    private UserAppService userAppService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (req.getCookies() != null) {
            Stream.of(req.getCookies()).filter(cookie -> cookie.getName().equals(TOKEN_COOKIE)).map(Cookie::getValue)
                    .forEach(token -> {
                        
                        if(jwtAuthentificationService.validateToken(token)){ // validation du token
                        
                        String username = jwtAuthentificationService.getSubject(token);
                            
                            try
                            {
                                UserApp userApp = userAppService.getUserApp(username);
                                UsernamePasswordAuthenticationToken auth =
                                        new UsernamePasswordAuthenticationToken(
                                                username,
                                                null,
                                                List.of(new SimpleGrantedAuthority(userApp.getRole()))
                                        );
                                SecurityContextHolder.getContext().setAuthentication(auth);
                            }
                            catch (Exception e)
                            {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        }
        filterChain.doFilter(req, response);
    }

}
