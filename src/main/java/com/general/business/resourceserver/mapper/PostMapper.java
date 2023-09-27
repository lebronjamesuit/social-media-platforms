package com.general.business.resourceserver.mapper;

import com.general.business.resourceserver.dto.PostResponse;
import com.general.business.resourceserver.model.Post;

import com.general.business.resourceserver.dto.PostRequest;
import com.general.business.resourceserver.model.Subreddit;
import com.general.business.resourceserver.repository.CommentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

      @Autowired
      private CommentRepository commentRepository;

      @Mapping(target= "createdDate", dateFormat = "dd-mm-yyyy")
      @Mapping(target= "subreddit", source = "subreddit")
      @Mapping(target= "description", expression = "java(postRequest.getDescription())")
      public abstract Post map(PostRequest postRequest, Subreddit subreddit);


      @Mapping(target = "id", source = "postId")
      @Mapping(target = "subredditName", expression = "java(post.getSubreddit().getName())")
      @Mapping(target = "commentCount", expression = "java(commentCount(post))")
      @Mapping(target = "duration", expression = "java(getDuration(post))")
      public abstract PostResponse mapToDto(Post post);


      Integer commentCount(Post post) {
            return commentRepository.findByPost(post).size();
      }

      String getDuration(Post post) {
            return post.getCreatedDate().toString();
      }



}
