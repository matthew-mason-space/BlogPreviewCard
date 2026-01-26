package com.mms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Column(name = "author_name", length = 255, nullable = false)
  private String authorName;

  @Column(name = "author_src", length = 255, nullable = false)
  private String authorSrc;

  @Column(name = "blog_category", length = 255, nullable = false)
  private String blogCategory;

  @Column(name = "blog_date", length = 255, nullable = false)
  private String blogDate;

  @Column(columnDefinition = "TEXT", name = "blog_description", nullable = false)
  private String blogDescription;

  @Column(name = "blog_image", length = 255, nullable = false)
  private String blogImage;

  @Column(name = "blog_title", length = 255, nullable = false)
  private String blogTitle;
}
