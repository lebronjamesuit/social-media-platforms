package com.social.media.confessionmedia.service;

import com.social.media.confessionmedia.config.JwtProvider;
import com.social.media.confessionmedia.dto.AuthenticationResponse;
import com.social.media.confessionmedia.dto.NotificationEmail;
import com.social.media.confessionmedia.dto.RegisterForm;
import com.social.media.confessionmedia.dto.RequestLogin;
import com.social.media.confessionmedia.exceptions.SocialGeneralException;
import com.social.media.confessionmedia.model.User;
import com.social.media.confessionmedia.model.VerificationToken;
import com.social.media.confessionmedia.repository.UserRepo;
import com.social.media.confessionmedia.repository.VerificationTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepo verificationTokenRepo;
    private MailService mailService;

    // AuthenticationManager is autowired from SecurityConfig init.
    @Autowired
    private AuthenticationManager authenticationManager;

    private JwtProvider jwtProvider;

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


    public void verificationRegisteredAccountByToken(String tokenValue) {
       Optional<VerificationToken> optionalVerificationToken =  verificationTokenRepo.findByTokenValue(tokenValue);
       optionalVerificationToken.orElseThrow(() -> new SocialGeneralException("Invalid token"));
       fetchUserAndEnable(optionalVerificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken token) {
        // Token entity has User_id, lazy load
       User u = token.getUser();
       u.setEnabled(true);
       userRepo.save(u);
    }

    public AuthenticationResponse login(RequestLogin requestLogin) throws Exception, AuthenticationException {
        // Create authentication core for user
        String tokenGenerated = "";
        UsernamePasswordAuthenticationToken userPwdAuthToken =
                new UsernamePasswordAuthenticationToken(requestLogin.getUserName(), requestLogin.getPassword());
        Authentication authenticationCore = authenticationManager.authenticate(userPwdAuthToken);
        // Generate token
        tokenGenerated = jwtProvider.generateToken(authenticationCore);


        return  AuthenticationResponse.builder()
                .authenticationToken(tokenGenerated)
                .username(requestLogin.getUserName())
                .expiresAt(Instant.now().plus(jwtProvider.getJwtExpirationInMinutes(), ChronoUnit.MINUTES))
                .build();
    }
}
