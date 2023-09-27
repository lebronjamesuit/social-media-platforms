package com.social.media.confessionmedia.resourceserver.service;


import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.authorizationserver.repository.UserRepo;
import com.social.media.confessionmedia.authorizationserver.service.AuthService;
import com.social.media.confessionmedia.resourceserver.dto.CommentsDto;
import com.social.media.confessionmedia.resourceserver.exceptions.PostNotFoundException;

import com.social.media.confessionmedia.resourceserver.mapper.CommentMapper;
import com.social.media.confessionmedia.resourceserver.model.Comment;

import com.social.media.confessionmedia.resourceserver.model.Post;

import com.social.media.confessionmedia.resourceserver.repository.CommentRepository;
import com.social.media.confessionmedia.resourceserver.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepo userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.mapToEntity(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).toList();
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
       Optional<User> user = userRepository.findByUserName(userName);
       user.stream().findAny().orElseThrow(() -> new UsernameNotFoundException(userName));

        return commentRepository.findAllByUser(user.get())
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }

}
