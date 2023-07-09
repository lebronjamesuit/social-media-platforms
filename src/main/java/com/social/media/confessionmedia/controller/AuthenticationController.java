package com.social.media.confessionmedia.controller;

import com.social.media.confessionmedia.dto.AuthenticationResponseDTO;
import com.social.media.confessionmedia.dto.NewAccessTokenRequestDTO;
import com.social.media.confessionmedia.dto.RegisterFormDTO;
import com.social.media.confessionmedia.dto.RequestLoginDTO;
import com.social.media.confessionmedia.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterFormDTO registerFormDTO){
        authService.signUp(registerFormDTO);
        return new ResponseEntity<>("User Register OK", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> logIn(@RequestBody RequestLoginDTO requestLoginDTO) throws Exception {
        AuthenticationResponseDTO authResponse  = authService.login(requestLoginDTO);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponseDTO> logout(@Valid @RequestBody NewAccessTokenRequestDTO tokenRequestDTO) {
        AuthenticationResponseDTO authResponse =  authService.logout(tokenRequestDTO);
        return new ResponseEntity(authResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh/newAccessToken")
    public AuthenticationResponseDTO refreshTokens(@Valid @RequestBody NewAccessTokenRequestDTO newAccessTokenRequestDTO) {
        return authService.requestNewAccessToken(newAccessTokenRequestDTO);
    }

    @GetMapping("/accountVerification/{tokenValue}")
    public ResponseEntity<String> accountVerification(@PathVariable("tokenValue") String tokenValue){
        authService.verificationRegisteredAccountByToken(tokenValue);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }


}
