package com.mms.blogpreviewcard.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter
@ToString
@Entity(name = "blog")
public class BlogEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "author_name", nullable = false)
  @NotBlank
  private String authorName;

  @Column(name = "author_src", nullable = false)
  @NotBlank
  private String authorSrc;

  @Column(name = "blog_category", nullable = false)
  @NotBlank
  private String blogCategory;

  @Column(name = "blog_date", nullable = false)
  @NotBlank
  private String blogDate;

  @Column(name = "blog_description", nullable = false, columnDefinition = "TEXT")
  @NotBlank
  private String blogDescription;

  @Column(name = "blog_image", nullable = false)
  @NotBlank
  private String blogImage;

  @Column(name = "blog_title", nullable = false)
  @NotBlank
  private String blogTitle;
}
