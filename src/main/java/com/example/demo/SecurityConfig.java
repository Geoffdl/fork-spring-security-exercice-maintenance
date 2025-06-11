package com.example.demo;

import com.example.demo.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter toto) throws Exception {
        http
              .csrf(csrf-> csrf.disable()) // ajout de csrf.disable pour activer post request sans gestion des csrf token
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/hello/public", "/auth/create-user","/auth/create-admin", "/auth/login", "/h2-console/**").permitAll()
                        .requestMatchers("/delete-contrat-by-id/").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // utile pour afficher h2-console
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                .addFilterBefore(toto, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
