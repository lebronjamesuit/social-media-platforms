package com.social.media.confessionmedia.repository;


import com.social.media.confessionmedia.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {

}
