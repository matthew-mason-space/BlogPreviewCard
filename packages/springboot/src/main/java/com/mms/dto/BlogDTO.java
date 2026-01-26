package com.mms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BlogDTO {
  @NotBlank
  private Long id;

  @NotBlank
  @Size(max = 255)
  private String authorName;

  @NotBlank
  @Size(max = 255)
  private String authorSrc;

  @NotBlank
  @Size(max = 255)
  private String blogCategory;

  @NotBlank
  @Size(max = 255)
  private String blogDate;

  @NotBlank
  private String blogDescription;

  @NotBlank
  @Size(max = 255)
  private String blogImage;

  @NotBlank
  @Size(max = 255)
  private String blogTitle;
}
