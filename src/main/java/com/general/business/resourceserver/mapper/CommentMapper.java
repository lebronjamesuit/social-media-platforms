package com.general.business.resourceserver.mapper;


import com.general.business.resourceserver.model.Post;
import com.general.business.resourceserver.dto.CommentsDto;
import com.general.business.resourceserver.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment mapToEntity(CommentsDto commentsDto, Post post);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    CommentsDto mapToDto(Comment comment);
}
