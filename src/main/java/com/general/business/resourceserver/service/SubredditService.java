package com.general.business.resourceserver.service;
import com.general.business.resourceserver.exceptions.SubredditNotFoundException;
import com.general.business.resourceserver.mapper.SubredditMapper;
import com.general.business.resourceserver.repository.SubredditRepository;
import com.general.business.resourceserver.dto.SubredditDto;
import com.general.business.resourceserver.model.Subreddit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubredditService {

    private SubredditMapper subredditMapper;
    private final SubredditRepository subredditRepository;

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream().map(item -> subredditMapper.entityToDTO(item))
                .collect(toList());
    }

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditMapper.dtoToEntity(subredditDto);
        subreddit.setCreatedDate(now());

        Subreddit subredditReturn = subredditRepository.save(subreddit);
        subredditDto.setId(subredditReturn.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id -" + id));
        return subredditMapper.entityToDTO(subreddit);
    }

}