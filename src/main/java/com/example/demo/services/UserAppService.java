package com.example.demo.services;

import com.example.demo.models.UserApp;
import com.example.demo.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private JwtAuthentificationService jwtAuthentificationService;
    @Autowired
    private BCryptPasswordEncoder bcrypt;

    public void createUserApp(UserApp userApp, String role) throws Exception {
        Optional<UserApp> userAppOptional = userAppRepository.findByUsername(userApp.getUsername());
        UserApp newUserApp = new UserApp(userApp.getUsername(), userApp.getPassword(), role);

        userAppRepository.save(
                newUserApp
        );
    }

    public ResponseCookie logUserApp(UserApp userApp) throws Exception {
        Optional<UserApp> userAppOptional = userAppRepository.findByUsername(userApp.getUsername());
        if(userAppOptional.isPresent() && bcrypt.matches(userAppOptional.get().getPassword(), userApp.getPassword()) ){
            return jwtAuthentificationService.generateToken(userApp.getUsername());
        }
        throw new Exception();
    }

    public UserApp getUserApp(String username){
        Optional<UserApp> userAppOptional = userAppRepository.findByUsername(username);
        if(userAppOptional.isPresent()){
            return userAppOptional.get();
        }
        return null;
    }

    public UserApp getUserAppById(Integer userId) throws Exception {
        Optional<UserApp> userAppOptional = userAppRepository.findById(userId);
        if(userAppOptional.isPresent()){
            return userAppOptional.get();
        }
        throw new Exception("User not found");
    }
}
