import cn from "../utilities/cn";

type props = {
  authorName: string;
  authorSrc: string;
};
export default function BlogCardAuthor({ authorName, authorSrc }: props) {
  return (
    <div className={cn("flex flex-row items-center gap-[1.2rem]")}>
      <img
        alt=""
        className={cn("h-[3.2rem] w-[3.2rem] rounded-[3.2rem]")}
        src={`${import.meta.env.VITE_API_STATIC_RESOURCES}/${authorSrc}`}
      />
      <h2
        className={cn(
          "font-figtree text-[1.4rem] leading-[150%] font-[800]",
          "text-gray-950 not-italic"
        )}>
        {authorName}
      </h2>
    </div>
  );
}
