INSERT INTO
  blog (
    id,
    author_name,
    author_src,
    blog_category,
    blog_date,
    blog_description,
    blog_image,
    blog_title
  )
VALUES
  (
    1,
    'Greg Hooper',
    'image-avatar.webp',
    'Learning',
    '21 Dec 2023',
    'These languages...',
    'illustration-article.svg',
    'HTML & CSS foundations'
  ) ON CONFLICT (id) DO NOTHING;