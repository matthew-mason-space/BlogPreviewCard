package com.mms.blogpreviewcard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BlogResourceNotFoundException extends RuntimeException {
  public BlogResourceNotFoundException(String message) {
    super(message);
  }
}
