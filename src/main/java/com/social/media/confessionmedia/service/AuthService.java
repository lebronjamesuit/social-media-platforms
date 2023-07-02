package com.social.media.confessionmedia.service;

import com.social.media.confessionmedia.dto.RegisterForm;
import com.social.media.confessionmedia.model.User;
import com.social.media.confessionmedia.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    public void signUp(RegisterForm registerForm){
        User user = new User();

        user.setEmail(registerForm.getEmail());
        user.setUserName(registerForm.getUserName());
        user.setEnabled(true);
        user.setCreated(Instant.now());
        user.setPassword(registerForm.getPassword());

        userRepo.save(user);
    }
}
