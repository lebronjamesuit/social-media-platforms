package com.general.business.resourceserver.mapper;

import com.general.business.resourceserver.dto.SubredditDto;
import com.general.business.resourceserver.model.Subreddit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-26T16:12:51+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class SubredditMapperImpl implements SubredditMapper {

    @Override
    public SubredditDto entityToDTO(Subreddit reddit) {
        if ( reddit == null ) {
            return null;
        }

        SubredditDto.SubredditDtoBuilder subredditDto = SubredditDto.builder();

        subredditDto.id( reddit.getId() );
        subredditDto.name( reddit.getName() );
        subredditDto.description( reddit.getDescription() );

        subredditDto.numberOfPosts( reddit.getPosts().size() );

        return subredditDto.build();
    }

    @Override
    public Subreddit dtoToEntity(SubredditDto dto) {
        if ( dto == null ) {
            return null;
        }

        Subreddit.SubredditBuilder subreddit = Subreddit.builder();

        subreddit.id( dto.getId() );
        subreddit.name( dto.getName() );
        subreddit.description( dto.getDescription() );

        return subreddit.build();
    }
}
