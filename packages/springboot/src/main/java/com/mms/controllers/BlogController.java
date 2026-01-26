package com.mms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.dto.BlogDTO;
import com.mms.services.BlogService;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {
    "http://localhost:4173",
    "http://localhost:5173"
})
@RequestMapping(path = "/api/blog")
@RequiredArgsConstructor
@RestController
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
  public ResponseEntity<BlogDTO> getBlog(
      @PathVariable @Positive Long id) throws InterruptedException {
    log.info("GET /api/blog/{} requested", id);
    Thread.sleep(2000);
    return ResponseEntity.ok(blogService.getById(id));
  }
}
