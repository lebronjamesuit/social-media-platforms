package com.social.media.confessionmedia.resourceserver.mapper;


import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.resourceserver.dto.CommentsDto;
import com.social.media.confessionmedia.resourceserver.model.Comment;
import com.social.media.confessionmedia.resourceserver.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment mapToEntity(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
}
