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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    private final String PATTERN_FORMAT = "dd/MM/YYYY hh:mm:ss";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());

    @Transactional
    public void signUp(RegisterFormDTO registerFormDTO) {
        User user = new User();
        user.setEmail(registerFormDTO.getEmail());
        user.setUserName(registerFormDTO.getUsername());
        user.setEnabled(false);  // UnActivated account
        user.setCreated(Instant.now());

        checkDuplicateUsernameOrEmail(user);

        user.setPassword(passwordEncoder.encode(registerFormDTO.getPassword()));
        userRepo.save(user);

        String tokenValue = generateVerification(user);
        mailService.sendRegisterConfirmationEmail(user, tokenValue);

    }

    private void checkDuplicateUsernameOrEmail(User user) {
        userRepo.findByUserNameOrEmail(user.getUserName(), user.getEmail())
                 .ifPresent( u -> {throw new RuntimeException("A user with this username or email already exists");});

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
                new UsernamePasswordAuthenticationToken(requestLoginDTO.getUsername(), requestLoginDTO.getPassword());

        Authentication authenticationCore = authenticationManager.authenticate(userPwdAuthToken);

        User userModel = (User) authenticationCore.getPrincipal();
        String accessToken = accessTokenService.generateNewAccessTokenByUserName(userModel.getUserName());
        String refreshedToken = refreshTokenService.generateAndSaveRefreshToken(userModel.getUserName());
        String accessTokenExpiration = formatter.format(Instant.now().plus(jwtProvider.getJwtExpirationInMinutes(), ChronoUnit.MINUTES));
        String refreshedExpiration = formatter.format(Instant.now().plus(jwtProvider.getRefreshExpiration(), ChronoUnit.MINUTES));
        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshedToken)
                .username(requestLoginDTO.getUsername())
                .accessTokenExpiresAt(accessTokenExpiration)
                .refreshTokenExpiresAt(refreshedExpiration)
                .build();
    }

    /// If access token is expired, call this method to request a new one
    public AuthenticationResponseDTO requestNewAccessToken(NewAccessTokenRequestDTO newAccessTokenRequestDTO) {
        String accessToken = accessTokenService.generateNewAccessTokenByRefreshToken(newAccessTokenRequestDTO);
        String accessTokenExpiration = formatter.format(Instant.now().plus(jwtProvider.getJwtExpirationInMinutes(), ChronoUnit.MINUTES));

        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(newAccessTokenRequestDTO.getRefreshToken())
                .username(newAccessTokenRequestDTO.getUsername())
                .accessTokenExpiresAt(accessTokenExpiration)
                .build();

    }

    public AuthenticationResponseDTO logout(NewAccessTokenRequestDTO tokenRequestDTO) {
        if(tokenRequestDTO.getRefreshToken().isEmpty() == false){
            Optional<User> userOp  = userRepo.findByUserName(tokenRequestDTO.getUsername());
            refreshTokenService.revokeAllUserRefreshedTokens(userOp.get());
        }

        return AuthenticationResponseDTO.builder()
                .accessToken(null)
                .refreshToken(null)
                .username(tokenRequestDTO.getUsername())
                .accessTokenExpiresAt(null)
                .build();

    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal  = (Jwt )SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return   userRepo.findByUserName(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    public boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.isAuthenticated() && !(auth  instanceof AnonymousAuthenticationToken);
    }

}