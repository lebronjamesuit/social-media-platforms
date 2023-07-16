package com.social.media.confessionmedia.resourceserver.mapper;

import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.resourceserver.dto.PostRequest;
import com.social.media.confessionmedia.resourceserver.dto.PostResponse;
import com.social.media.confessionmedia.resourceserver.model.Post;
import com.social.media.confessionmedia.resourceserver.model.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class PostMapper {

      @Mapping(target= "voteCount", constant = "0")
      @Mapping(target= "createdDate", dateFormat = "dd-mm-yyyy")
      @Mapping(target= "user", source = "user")
      @Mapping(target= "subreddit", source = "subreddit")
      @Mapping(target= "description", expression = "java(postRequest.getDescription())")
      public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);


      @Mapping(target = "id", source = "postId")
      @Mapping(target = "subredditName", expression = "java(post.getSubreddit().getName())")
      @Mapping(target = "userName", expression = "java(post.getUser().getUserName())")
      @Mapping(target = "commentCount", constant = "0")
      @Mapping(target = "duration", constant = "0")
      @Mapping(target = "upVote", constant = "true")
      @Mapping(target = "downVote", constant = "false")
      public abstract PostResponse mapToDto(Post post);

      //PostResponse mapToDto(Post post);
}
