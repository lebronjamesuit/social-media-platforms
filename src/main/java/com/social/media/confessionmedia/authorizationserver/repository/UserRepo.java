package com.social.media.confessionmedia.authorizationserver.repository;

import com.social.media.confessionmedia.authorizationserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String username);
    Optional<User> findByUserNameOrEmail(String username, String email);
}
