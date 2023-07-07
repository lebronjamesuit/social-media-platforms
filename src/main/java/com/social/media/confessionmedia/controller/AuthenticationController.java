package com.social.media.confessionmedia.controller;

import com.social.media.confessionmedia.dto.AuthenticationResponse;
import com.social.media.confessionmedia.dto.RegisterForm;
import com.social.media.confessionmedia.dto.RequestLogin;
import com.social.media.confessionmedia.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    /*public AuthenticationController(AuthService authenticationService){
        this.authService = authenticationService;
    }*/

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody RequestLogin requestLogin) throws Exception {
        AuthenticationResponse authResponse  = authService.login(requestLogin);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterForm registerForm){
        authService.signUp(registerForm);
        return new ResponseEntity<>("User Register OK", HttpStatus.OK);
    }

    @GetMapping("/helloAuth")
    public String getString(){
       return "hello Auth";
    }

    @GetMapping("/accountVerification/{tokenValue}")
    public ResponseEntity<String> accountVerification(@PathVariable("tokenValue") String tokenValue){
        authService.verificationRegisteredAccountByToken(tokenValue);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }


}
