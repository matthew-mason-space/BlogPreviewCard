package com.mms.blogpreviewcard.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.blogpreviewcard.dto.BlogDTO;
import com.mms.blogpreviewcard.services.BlogService;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/blog")
@RequiredArgsConstructor
@Validated
public class BlogController {
  private static final Logger log = LoggerFactory
      .getLogger(BlogController.class);

  private final BlogService blogService;

  @GetMapping(path = {
      "/{id}"
  }, produces = {
      MediaType.APPLICATION_JSON_VALUE
  })
  public BlogDTO getBlog(
      @PathVariable @Positive Long id) {
    log.info("GET /api/blog/{} requested", id);
    return blogService.getById(id);
  }
}
