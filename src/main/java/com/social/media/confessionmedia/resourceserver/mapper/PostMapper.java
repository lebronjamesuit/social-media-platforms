package com.social.media.confessionmedia.resourceserver.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.social.media.confessionmedia.authorizationserver.model.User;
import com.social.media.confessionmedia.authorizationserver.service.AuthService;
import com.social.media.confessionmedia.resourceserver.dto.PostRequest;
import com.social.media.confessionmedia.resourceserver.dto.PostResponse;
import com.social.media.confessionmedia.resourceserver.model.Post;
import com.social.media.confessionmedia.resourceserver.model.Subreddit;
import com.social.media.confessionmedia.resourceserver.model.Vote;
import com.social.media.confessionmedia.resourceserver.model.VoteType;
import com.social.media.confessionmedia.resourceserver.repository.CommentRepository;
import com.social.media.confessionmedia.resourceserver.repository.VoteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import static com.social.media.confessionmedia.resourceserver.model.VoteType.*;
import java.util.Optional;


@Mapper(componentModel = "spring")
public abstract class PostMapper {

      @Autowired
      private CommentRepository commentRepository;
      @Autowired
      private VoteRepository voteRepository;
      @Autowired
      private AuthService authService;

      @Mapping(target= "voteCount", constant = "0")
      @Mapping(target= "createdDate", dateFormat = "dd-mm-yyyy")
      @Mapping(target= "user", source = "user")
      @Mapping(target= "subreddit", source = "subreddit")
      @Mapping(target= "description", expression = "java(postRequest.getDescription())")
      public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);


      @Mapping(target = "id", source = "postId")
      @Mapping(target = "subredditName", expression = "java(post.getSubreddit().getName())")
      @Mapping(target = "userName", expression = "java(post.getUser().getUserName())")
      @Mapping(target = "commentCount", expression = "java(commentCount(post))")
      @Mapping(target = "duration", expression = "java(getDuration(post))")
      @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
      @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
      public abstract PostResponse mapToDto(Post post);


      Integer commentCount(Post post) {
            return commentRepository.findByPost(post).size();
      }

      String getDuration(Post post) {
            return TimeAgo.using(post.getCreatedDate().toEpochMilli());
      }

      boolean isPostUpVoted(Post post) {
            return checkVoteType(post, UPVOTE);
      }

      boolean isPostDownVoted(Post post) {
            return checkVoteType(post, DOWNVOTE);
      }

      private boolean checkVoteType(Post post, VoteType voteType) {
            if (authService.isLoggedIn()) {
                  Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                          authService.getCurrentUser());
                  return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                          .isPresent();
            }
            return false;
      }
}
