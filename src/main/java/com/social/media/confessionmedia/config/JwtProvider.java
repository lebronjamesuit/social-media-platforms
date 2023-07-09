package com.social.media.confessionmedia.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@RequiredArgsConstructor
@Service
public class JwtProvider {

    // Some objects are Autowired from SecurityConfig init such as JwtEncoder
    @Autowired
    private JwtEncoder jwtEncoder;

    @Value("${jwt.access.token.expire.minutes}")
    private long jwtExpirationInMinutes;

    @Value("${jwt.refreshed.token.expire.minutes}")
    private long refreshExpiration;

    public long getJwtExpirationInMinutes() {
        return this.jwtExpirationInMinutes;
    }

    public long getRefreshExpiration() {
        return this.refreshExpiration;
    }

    public String generateToken(String userName) {
        return generateTokenWithUserNameAndRole(userName, jwtExpirationInMinutes, "ROLE_USER");
    }

    private String generateTokenWithUserNameAndRole(String username, long minutesExpired, String roleValue) {
        JwtClaimsSet claimSet = JwtClaimsSet.builder()
                .issuer("self")
                .subject(username)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(minutesExpired, ChronoUnit.MINUTES))
                .claim("scope", roleValue)
                .build();

        Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(claimSet));
        return jwt.getTokenValue();
    }

    public String generatingRefreshToken(String username) {
        JwtClaimsSet claimSet = JwtClaimsSet.builder()
                .issuer("self")
                .subject(username)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(refreshExpiration, ChronoUnit.MINUTES))
                .claim("scope", "REFRESHED")
                .build();

        Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(claimSet));
        return jwt.getTokenValue();
    }

}





