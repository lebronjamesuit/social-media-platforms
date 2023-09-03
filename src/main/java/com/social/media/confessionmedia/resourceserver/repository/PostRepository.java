package com.social.media.confessionmedia.resourceserver.repository;

import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.resourceserver.model.Post;
import com.social.media.confessionmedia.resourceserver.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);

    List<Post> findAllByOrderByPostIdDesc();

}
