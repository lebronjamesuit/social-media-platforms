package com.social.media.confessionmedia.config;


import com.social.media.confessionmedia.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Service
public class JwtProvider {

    // Some objects are Autowired from SecurityConfig init such as JwtEncoder
    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${jwt.expire.min}")
    private long jwtExpirationInMinutes;

    public String generateToken (Authentication authentication){
       // org.springframework.security.core.userdetails.User
        User userModel = (User)  authentication.getPrincipal();
       return generateTokenWithUserName(userModel.getUsername());
    }

    private String generateTokenWithUserName(String username) {

        JwtClaimsSet claimSet  = JwtClaimsSet.builder()
                .issuer("self")
                .subject(username)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(jwtExpirationInMinutes, ChronoUnit.MINUTES))
                .claim("scope", "ROLE_USER")
                .build();

        Jwt jwt =  this.jwtEncoder.encode(JwtEncoderParameters.from(claimSet));
        return jwt.getTokenValue();
    }

    public long getJwtExpirationInMinutes() {
        return jwtExpirationInMinutes;
    }
}
