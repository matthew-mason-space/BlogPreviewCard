package com.mms.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mms.entities.BlogEntity;

public interface BlogRepository
    extends CrudRepository<BlogEntity, Long> {
}
