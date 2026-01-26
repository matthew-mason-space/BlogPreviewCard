package com.mms.blogpreviewcard.services;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.blogpreviewcard.dto.BlogDTO;
import com.mms.blogpreviewcard.exceptions.BlogResourceNotFoundException;
import com.mms.blogpreviewcard.mappers.BlogMapper;
import com.mms.blogpreviewcard.repositories.BlogRepository;

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
