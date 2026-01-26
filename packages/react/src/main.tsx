import "./main.css";

import { createRoot } from "react-dom/client";
import { StrictMode } from "react";

import BlogCard from "./components/BlogCard";
import cn from "./utilities/cn";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <main
      className={cn("bg-yellow flex min-h-screen items-center justify-center")}>
      <BlogCard />
    </main>
  </StrictMode>
);
