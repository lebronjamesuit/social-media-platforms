package com.social.media.confessionmedia.controller;

import com.social.media.confessionmedia.dto.RegisterForm;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterForm registerForm){
        authService.signUp(registerForm);
        return new ResponseEntity<>("User Register OK", HttpStatus.OK);
    }


    @GetMapping("/helloAuth")
    public String getString(){
       return "hello Auth";
    }

}
