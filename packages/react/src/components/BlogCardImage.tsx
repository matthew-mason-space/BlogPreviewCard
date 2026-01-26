import cn from "../utilities/cn";

type props = {
  blogImage: string;
};
export default function BlogCardImage({ blogImage }: props) {
  return (
    <img
      alt=""
      className={cn("rounded-[1rem]")}
      src={`${import.meta.env.VITE_API_STATIC_RESOURCES}/${blogImage}`}
    />
  );
}
