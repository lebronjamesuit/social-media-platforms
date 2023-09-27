package com.general.business.resourceserver.repository;

import com.general.business.resourceserver.model.Post;
import com.general.business.resourceserver.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByOrderByPostIdDesc();

}
