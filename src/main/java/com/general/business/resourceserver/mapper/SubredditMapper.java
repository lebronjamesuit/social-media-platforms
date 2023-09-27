package com.general.business.resourceserver.mapper;

import com.general.business.resourceserver.dto.SubredditDto;
import com.general.business.resourceserver.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

        @Mapping(target = "numberOfPosts", expression = "java(reddit.getPosts().size())")
        SubredditDto entityToDTO(Subreddit reddit);


         @InheritInverseConfiguration
         @Mapping(target = "posts", ignore = true )
        Subreddit dtoToEntity(SubredditDto dto);

}

// https://mapstruct.org/documentation/dev/api/org/mapstruct/InheritInverseConfiguration.html