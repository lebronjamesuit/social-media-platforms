package com.social.media.confessionmedia.authorizationserver.service;

import com.social.media.confessionmedia.authorizationserver.config.JwtProvider;
import com.social.media.confessionmedia.authorizationserver.dto.*;
import com.social.media.confessionmedia.authorizationserver.exceptions.SocialGeneralException;
import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.authorizationserver.model.VerificationToken;
import com.social.media.confessionmedia.authorizationserver.repository.VerificationTokenRepo;
import com.social.media.confessionmedia.authorizationserver.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private AccessTokenService accessTokenService;
    private RefreshTokenService refreshTokenService;

    @Transactional
    public void signUp(RegisterFormDTO registerFormDTO) {
        User user = new User();
        user.setEmail(registerFormDTO.getEmail());
        user.setUserName(registerFormDTO.getUserName());
        user.setEnabled(false);  // UnActivated account
        user.setCreated(Instant.now());

        user.setPassword(passwordEncoder.encode(registerFormDTO.getPassWord()));
        userRepo.save(user);

        String tokenValue = generateVerification(user);

        NotificationEmailDTO notifiedEmail = new NotificationEmailDTO();
        notifiedEmail.setRecipient(user.getEmail());
        notifiedEmail.setSubject("Confirmation registration");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Welcome to our social media:  " + user.getUserName());
        stringBuilder.append("One more step is click the link bellow to activate your account : ");
        stringBuilder.append("http://localhost:8600/api/auth/accountVerification/" + tokenValue);

        notifiedEmail.setBody(stringBuilder.toString());
        mailService.sendEmail(notifiedEmail);

    }

    private String generateVerification(User user) {
        String tokenValue = UUID.randomUUID().toString();

        VerificationToken vt = new VerificationToken();
        vt.setTokenValue(tokenValue);
        vt.setUser(user);
        vt.setCreated(Instant.now());
        verificationTokenRepo.save(vt);

        return tokenValue;
    }


    public void verificationRegisteredAccountByToken(String tokenValue) {
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepo.findByTokenValue(tokenValue);
        optionalVerificationToken.orElseThrow(() -> new SocialGeneralException("Invalid token"));
        fetchUserAndEnable(optionalVerificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken token) {
        // Token entity has User_id, lazy load
        User u = token.getUser();
        u.setEnabled(true);
        userRepo.save(u);
    }

    @Transactional
    public AuthenticationResponseDTO login(RequestLoginDTO requestLoginDTO) throws Exception, AuthenticationException {

        UsernamePasswordAuthenticationToken userPwdAuthToken =
                new UsernamePasswordAuthenticationToken(requestLoginDTO.getUserName(), requestLoginDTO.getPassWord());

        Authentication authenticationCore = authenticationManager.authenticate(userPwdAuthToken);

        User userModel = (User) authenticationCore.getPrincipal();
        String accessToken = accessTokenService.generateNewAccessTokenByUserName(userModel.getUserName());
        String refreshedToken = refreshTokenService.generateAndSaveRefreshToken(userModel.getUserName());

        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshedToken)
                .userName(requestLoginDTO.getUserName())
                .accessTokenExpiresAt(Instant.now().plus(jwtProvider.getJwtExpirationInMinutes(), ChronoUnit.MINUTES))
                .refreshTokenExpiresAt(Instant.now().plus(jwtProvider.getRefreshExpiration(), ChronoUnit.MINUTES))
                .build();
    }

    /// If access token is expired, call this method to request a new one
    public AuthenticationResponseDTO requestNewAccessToken(NewAccessTokenRequestDTO newAccessTokenRequestDTO) {
        String accessToken = accessTokenService.generateNewAccessTokenByRefreshToken(newAccessTokenRequestDTO);
        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(newAccessTokenRequestDTO.getRefreshToken())
                .userName(newAccessTokenRequestDTO.getUserName())
                .accessTokenExpiresAt(Instant.now().plus(jwtProvider.getJwtExpirationInMinutes(), ChronoUnit.MINUTES))
                .build();

    }

    public AuthenticationResponseDTO logout(NewAccessTokenRequestDTO tokenRequestDTO) {
        if(tokenRequestDTO.getRefreshToken().isEmpty() == false){
            Optional<User> userOp  = userRepo.findByUserName(tokenRequestDTO.getUserName());
            refreshTokenService.revokeAllUserRefreshedTokens(userOp.get());
        }

        return AuthenticationResponseDTO.builder()
                .accessToken(null)
                .refreshToken(null)
                .userName(tokenRequestDTO.getUserName())
                .accessTokenExpiresAt(null)
                .build();

    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal  = (Jwt )SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return   userRepo.findByUserName(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }


}