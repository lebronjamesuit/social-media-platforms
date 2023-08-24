package com.social.media.confessionmedia.resourceserver.repository;

import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.resourceserver.model.Post;
import com.social.media.confessionmedia.resourceserver.model.Subreddit;
import com.social.media.confessionmedia.resourceserver.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}

// Commons functions
 /*
     Page<User> findByLastname(String lastname, Pageable pageable);

    Slice<User> findByLastname(String lastname, Pageable pageable);

    Window<User> findTop10ByLastname(String lastname, ScrollPosition position, Sort sort);

    List<User> findByLastname(String lastname, Sort sort);

    List<User> findByLastname(String lastname, Pageable pageable);*/