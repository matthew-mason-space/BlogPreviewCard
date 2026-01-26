import cn from "../utilities/cn";

export default function BlogCardLoading() {
  return (
    <div
      aria-live="polite"
      className={cn("inline-flex items-center gap-3 text-current")}
      role="status">
      <svg
        aria-hidden="true"
        className={cn("h-[5rem] w-[5rem] animate-spin")}
        viewBox="0 0 50 50">
        <circle
          className={cn("stroke-current opacity-25")}
          cx="25"
          cy="25"
          r="20"
          fill="none"
          strokeWidth="4"
        />
        <path
          className={cn("stroke-current stroke-[4]")}
          d="M45 25a20 20 0 0 1-20 20"
          strokeLinecap="round"
          fill="none"
        />
      </svg>
    </div>
  );
}
