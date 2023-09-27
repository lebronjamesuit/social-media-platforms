package com.social.media.confessionmedia.authorizationserver.repository;


import com.social.media.confessionmedia.authorizationserver.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {

     Optional<VerificationToken> findByTokenValue(String tokenValue);

}
