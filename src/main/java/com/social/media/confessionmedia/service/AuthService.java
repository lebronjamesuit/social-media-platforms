package com.social.media.confessionmedia.service;

import com.social.media.confessionmedia.dto.NotificationEmail;
import com.social.media.confessionmedia.dto.RegisterForm;
import com.social.media.confessionmedia.model.User;
import com.social.media.confessionmedia.model.VerificationToken;
import com.social.media.confessionmedia.repository.UserRepo;
import com.social.media.confessionmedia.repository.VerificationTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepo verificationTokenRepo;
    private MailService mailService;

    @Transactional
    public void signUp(RegisterForm registerForm){
        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setUserName(registerForm.getUserName());
        user.setEnabled(false);  // UnActivated account
        user.setCreated(Instant.now());

        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        userRepo.save(user);

        String tokenValue = generateVerification(user);

        NotificationEmail notiEmail = new NotificationEmail();
        notiEmail.setRecipient(user.getEmail());
        notiEmail.setSubject("Confirmation registration");

        StringBuilder stringBuilder  = new StringBuilder();
        stringBuilder.append("Welcome to our social media:  " +  user.getUserName());
        stringBuilder.append("One more step is click the link bellow to activate your account : ");
        stringBuilder.append("http://localhost:8600/api/auth/accountVerification/" + tokenValue);

        notiEmail.setBody(stringBuilder.toString());

        mailService.sendEmail(notiEmail);

    }

    private String generateVerification(User user) {
        String tokenValue =  UUID.randomUUID().toString();

        VerificationToken vt = new VerificationToken();
        vt.setTokenValue(tokenValue);
        vt.setUser(user);
        vt.setCreated(Instant.now());
        verificationTokenRepo.save(vt);

        return tokenValue;
    }


    public void verificationToken(String tokenValue) {
       VerificationToken  vt =  verificationTokenRepo.findByTokenValue(tokenValue);
       User user =  vt.getUser();
       user.setEnabled(true);
       userRepo.save(user);

    }
}
