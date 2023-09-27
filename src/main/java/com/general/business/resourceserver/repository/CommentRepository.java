package com.general.business.resourceserver.repository;
import com.general.business.resourceserver.model.Comment;
import com.general.business.resourceserver.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

}

