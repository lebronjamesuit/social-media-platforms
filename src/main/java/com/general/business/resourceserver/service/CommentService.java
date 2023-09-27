package com.general.business.resourceserver.service;

import com.general.business.resourceserver.model.Post;
import com.general.business.resourceserver.dto.CommentsDto;
import com.general.business.resourceserver.exceptions.PostNotFoundException;
import com.general.business.resourceserver.mapper.CommentMapper;
import com.general.business.resourceserver.model.Comment;
import com.general.business.resourceserver.repository.CommentRepository;
import com.general.business.resourceserver.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.mapToEntity(commentsDto, post);
        commentRepository.save(comment);

    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).toList();
    }

}
