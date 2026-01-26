package com.mms.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class BlogErrorDTO {
  private Instant timestamp;
  private int status;
  private String error;
  private String message;
  private String path;
  private String trace;
}
