package com.social.media.confessionmedia.controller;

import com.social.media.confessionmedia.dto.RegisterForm;
import com.social.media.confessionmedia.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void signUp(@RequestBody RegisterForm registerForm){
        authService.signUp(registerForm);
    }


    @GetMapping("/helloAuth")
    public String getString(){
       return "hello Auth";
    }

}
