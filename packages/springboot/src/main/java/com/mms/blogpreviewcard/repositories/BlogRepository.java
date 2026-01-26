package com.mms.blogpreviewcard.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mms.blogpreviewcard.entities.BlogEntity;

public interface BlogRepository
    extends CrudRepository<BlogEntity, Long> {
}
