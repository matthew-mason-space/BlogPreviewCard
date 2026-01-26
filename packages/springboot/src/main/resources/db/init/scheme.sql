CREATE TABLE
  IF NOT EXISTS blog (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    author_name VARCHAR(255) NOT NULL,
    author_src VARCHAR(255) NOT NULL,
    blog_category VARCHAR(255) NOT NULL,
    blog_date VARCHAR(255) NOT NULL,
    blog_description TEXT NOT NULL,
    blog_image VARCHAR(255) NOT NULL,
    blog_title VARCHAR(255) NOT NULL
  );