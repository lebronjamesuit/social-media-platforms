package com.general.business.resourceserver.repository;

import com.general.business.resourceserver.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostPagingRepository extends  PagingAndSortingRepository<Post, Long>{

    @Override
    Page<Post> findAll(Pageable pageable);
}
