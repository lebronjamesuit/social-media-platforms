package com.social.media.confessionmedia.resourceserver.service;
import com.social.media.confessionmedia.authorizationserver.service.AuthService;
import com.social.media.confessionmedia.resourceserver.dto.SubredditDto;
import com.social.media.confessionmedia.resourceserver.exceptions.SubredditNotFoundException;
import com.social.media.confessionmedia.resourceserver.mapper.SubredditMapper;
import com.social.media.confessionmedia.resourceserver.model.Subreddit;
import com.social.media.confessionmedia.resourceserver.repository.SubredditRepository;
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
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream().map(item -> subredditMapper.entityToDTO(item))
                .collect(toList());
    }

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditMapper.dtoToEntity(subredditDto);
        subreddit.setUser(authService.getCurrentUser());
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