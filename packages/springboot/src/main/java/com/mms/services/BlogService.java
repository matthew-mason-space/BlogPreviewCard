package com.mms.services;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.dto.BlogDTO;
import com.mms.exceptions.BlogResourceNotFoundException;
import com.mms.mappers.BlogMapper;
import com.mms.repositories.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
  private final BlogMapper mapper;
  private final BlogRepository blogRepository;

  @Transactional(readOnly = true)
  public BlogDTO getById(Long id) {
    Objects.requireNonNull(id, "id must not be null");
    return blogRepository
        .findById(id)
        .map(mapper::toDto)
        .orElseThrow(() -> {
          return new BlogResourceNotFoundException("Blog not found: " + id);
        });

  }
}
