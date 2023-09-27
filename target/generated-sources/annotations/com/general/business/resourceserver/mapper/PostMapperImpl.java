package com.general.business.resourceserver.mapper;

import com.general.business.resourceserver.dto.PostRequest;
import com.general.business.resourceserver.dto.PostResponse;
import com.general.business.resourceserver.model.Post;
import com.general.business.resourceserver.model.Subreddit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-26T16:12:50+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class PostMapperImpl extends PostMapper {

    @Override
    public Post map(PostRequest postRequest, Subreddit subreddit) {
        if ( postRequest == null && subreddit == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        if ( postRequest != null ) {
            post.postId( postRequest.getPostId() );
            post.postName( postRequest.getPostName() );
            post.url( postRequest.getUrl() );
        }
        if ( subreddit != null ) {
            post.createdDate( subreddit.getCreatedDate() );
            post.subreddit( subreddit );
        }
        post.description( postRequest.getDescription() );

        return post.build();
    }

    @Override
    public PostResponse mapToDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setId( post.getPostId() );
        postResponse.setPostName( post.getPostName() );
        postResponse.setUrl( post.getUrl() );
        postResponse.setDescription( post.getDescription() );

        postResponse.setSubredditName( post.getSubreddit().getName() );
        postResponse.setCommentCount( commentCount(post) );
        postResponse.setDuration( getDuration(post) );

        return postResponse;
    }
}
