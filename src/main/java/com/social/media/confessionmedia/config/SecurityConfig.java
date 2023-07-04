package com.social.media.confessionmedia.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// This is Spring boot 3, Spring security 6.1
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests( (authz) ->
                authz.requestMatchers("/api/auth/**")
                        .permitAll()
                        .anyRequest().authenticated()
        );

        // 401 possible to be authenticated
        http.httpBasic(Customizer.withDefaults());

        http.csrf().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder factoryPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}