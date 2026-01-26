import { useEffect, useState } from "react";

import type { BlogResponseError } from "../types/BlogResponseError";
import type { BlogResponse } from "../types/BlogResponse";
import BlogCardLoading from "./BlogCardLoading";
import BlogCardContent from "./BlogCardContent";
import BlogCardAuthor from "./BlogCardAuthor";
import BlogCardImage from "./BlogCardImage";
import BlogCardError from "./BlogCardError";
import cn from "../utilities/cn";

export default function BlogCard() {
  const [blog, setBlog] = useState<BlogResponse | null>(null);
  const [error, setError] = useState<BlogResponseError | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      setError(null);
      setLoading(true);

      try {
        const response = await fetch(`${import.meta.env.VITE_API}/api/blog/1`, {
          headers: {
            Accept: "application/json",
          },
          method: "GET",
        });

        if (!response.status) {
          throw new Error("NetworkError");
        }

        const json = await response.json();
        switch (response.status) {
          case 200:
            setBlog(json);
            break;
          case 400:
          case 404:
            setError(json);
            break;
          default:
            throw new Error("NetworkError");
        }
      } catch (error) {
        if ((error as any)?.name !== "AbortError") {
          console.error(error);
        }
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <BlogCardLoading />;
  }

  if (error) {
    return <BlogCardError error={error} />;
  }

  if (blog) {
    return (
      <div
        className={cn(
          "flex max-w-[32.7rem] flex-col gap-[2.4rem] rounded-[2rem]",
          "border-[0.1rem] border-solid border-gray-950 bg-white",
          "p-[2.3rem] shadow-[0.8rem_0.8rem_0_0_#000]"
        )}>
        <BlogCardImage blogImage={blog!.blogImage} />
        <BlogCardContent
          blogCategory={blog!.blogCategory}
          blogDate={blog!.blogDate}
          blogDescription={blog!.blogDescription}
          blogTitle={blog!.blogTitle}
        />
        <BlogCardAuthor
          authorName={blog!.authorName}
          authorSrc={blog!.authorSrc}
        />
      </div>
    );
  }
}
