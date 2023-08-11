package com.social.media.confessionmedia.authorizationserver.service;

import com.social.media.confessionmedia.authorizationserver.dto.NewAccessTokenRequestDTO;
import com.social.media.confessionmedia.authorizationserver.config.JwtProvider;
import com.social.media.confessionmedia.authorizationserver.model.Token;
import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.authorizationserver.repository.TokenRepo;
import com.social.media.confessionmedia.authorizationserver.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class AccessTokenService {

    private final JwtProvider jwtProvider;
    private final TokenRepo tokenRepo;
    private final UserRepo userRepo;

    public String generateNewAccessTokenByUserName(String userName){
       return  jwtProvider.generateToken(userName);
    }

    public String generateNewAccessTokenByRefreshToken(NewAccessTokenRequestDTO newAccessTokenRequestDTO){
        Optional<User> userOptional =  userRepo.findByUserName(newAccessTokenRequestDTO.getUsername());
        User user = userOptional.get();

        // Verify refreshed token is still valid before generate new access token
        List<Token> tokens = tokenRepo.findAllValidTokenByUser(user.getUserId());
        boolean matched =  tokens.stream().filter(token ->
                !token.isExpired() && !token.isRevoked())
                .anyMatch( token ->
                token.getToken().equals(newAccessTokenRequestDTO.getRefreshToken()));
           if(matched){
           return jwtProvider.generateToken(user.getUserName());
        }
        return "Refresh token is invalid, unable to request a new access token";
    }

}
