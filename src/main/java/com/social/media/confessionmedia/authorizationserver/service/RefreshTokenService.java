package com.social.media.confessionmedia.authorizationserver.service;

import com.social.media.confessionmedia.authorizationserver.model.Token;
import com.social.media.confessionmedia.authorizationserver.model.TokenType;
import com.social.media.confessionmedia.authorizationserver.repository.TokenRepo;
import com.social.media.confessionmedia.authorizationserver.config.JwtProvider;
import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.authorizationserver.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final TokenRepo tokenRepo;
    private final JwtProvider jwtProvider;
    private final UserRepo userRepo;

    /*public boolean checkRefreshTokenRevokedOrExpired(String jwtValue){
       Optional<Token> token  =  tokenRepo.findByToken(jwtValue);
       if(token.isPresent()){
        return  token.map(t -> !t.isExpired() && !t.isRevoked())
                 .orElse(false);
       }
       return true;
    }*/

    public String generateAndSaveRefreshToken(String username) {
        Optional<User> userOp = userRepo.findByUserName(username);
        // Always revoke all tokens of this user before allocate a new one
        revokeAllUserRefreshedTokens(userOp.get());

        String refreshToken = jwtProvider.generatingRefreshToken(username);
        Token refreshedToken =  Token.builder().tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .token(refreshToken)
                .user(userOp.get()).build();
        tokenRepo.save(refreshedToken);
        return refreshToken ;
    }

    public int revokeAllUserRefreshedTokens(User user) {
        AtomicInteger count = new AtomicInteger();
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return 0;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            count.getAndIncrement();
        });
        tokenRepo.saveAll(validUserTokens);
        return count.get();
    }
}
