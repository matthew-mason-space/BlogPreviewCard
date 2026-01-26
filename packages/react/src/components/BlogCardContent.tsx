import cn from "../utilities/cn";

type props = {
  blogCategory: string;
  blogDate: string;
  blogDescription: string;
  blogTitle: string;
};
export default function BlogCardContent({
  blogCategory,
  blogDate,
  blogDescription,
  blogTitle,
}: props) {
  return (
    <div className={cn("flex flex-col gap-[1.2rem]")}>
      <div
        className={cn(
          "bg-yellow max-w-fit rounded-[0.4rem] p-[0.4rem_1.2rem]"
        )}>
        <h2
          className={cn(
            "font-figtree text-[1.2rem] leading-[150%] font-[800]",
            "text-gray-950 not-italic"
          )}>
          {blogCategory}
        </h2>
      </div>
      <h4
        className={cn(
          "font-figtree text-[1.2rem] leading-[150%] font-[500]",
          "text-gray-950 not-italic"
        )}>
        Published {blogDate}
      </h4>
      <h1
        className={cn(
          "font-figtree text-[2rem] leading-[150%] font-[800]",
          "text-gray-950 not-italic"
        )}>
        {blogTitle}
      </h1>
      <p
        className={cn(
          "font-figtree text-[1.4rem] leading-[150%] font-[500]",
          "text-gray-500 not-italic"
        )}>
        {blogDescription}
      </p>
    </div>
  );
}
