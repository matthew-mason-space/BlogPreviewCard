package com.mms.blogpreviewcard.services;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.blogpreviewcard.dto.BlogDTO;
import com.mms.blogpreviewcard.entities.BlogEntity;
import com.mms.blogpreviewcard.exceptions.BlogResourceNotFoundException;
import com.mms.blogpreviewcard.repositories.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
  private final BlogRepository blogRepository;

  @Transactional(readOnly = true)
  public BlogDTO getById(Long id) {
    Objects.requireNonNull(id, "id must not be null");
    Optional<BlogDTO> blogDTO = blogRepository
        .findById(id)
        .map(this::toDto);

    if (blogDTO.isEmpty())
      throw new BlogResourceNotFoundException("Blog not found: " + id);

    return blogDTO.get();
  }

  private BlogDTO toDto(BlogEntity entity) {
    return new BlogDTO(
        entity.getId(),
        entity.getAuthorName(),
        entity.getAuthorSrc(),
        entity.getBlogCategory(),
        entity.getBlogDate(),
        entity.getBlogDescription(),
        entity.getBlogImage(),
        entity.getBlogTitle());
  }
}
