package com.social.media.confessionmedia.authorizationserver.repository;

import com.social.media.confessionmedia.authorizationserver.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String token);

    @Query(value = " SELECT * FROM token T WHERE T.USER_ID = ?1 "
            , nativeQuery = true)
    List<Token> findAllValidTokenByUser(Long userId);

}
