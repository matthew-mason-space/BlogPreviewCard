package com.mms.blogpreviewcard.mappers;

import org.springframework.stereotype.Component;

import com.mms.blogpreviewcard.dto.BlogDTO;
import com.mms.blogpreviewcard.entities.BlogEntity;

@Component
public class BlogMapper {
  public BlogDTO toDto(BlogEntity entity) {

    if (entity == null)
      return null;

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
