import type { BlogResponseError } from "../types/BlogResponseError";
import cn from "../utilities/cn";

type props = {
  error: BlogResponseError;
};
export default function BlogCardError({ error }: props) {
  const details =
    typeof error.error === "object"
      ? (error.error as { details?: string[] }).details
      : undefined;

  return (
    <div
      className={cn(
        "mx-[1rem] flex flex-col gap-[1rem] rounded-[1rem] border",
        "border-red-600 bg-red-50 p-4 text-sm text-red-800"
      )}
      role="alert">
      <h1 className={cn("text-[1.5rem]")}>
        Error code: {error.status ? ` ${error.status}` : ""}
      </h1>

      <h2 className={cn("text-[1.5rem]")}>
        Error message:{" "}
        {typeof error.error === "string" ? error.error : error.message}
      </h2>

      {details && details.length > 0 ? (
        <ul className={cn("list-disc pl-5")}>
          {details.map((detail, key) => (
            <li key={key}>
              <h2 className={cn("text-[1.2rem]")}>{detail}</h2>
            </li>
          ))}
        </ul>
      ) : null}

      {error.path ? (
        <h2 className={cn("text-[1.5rem]")}>Path: {error.path}</h2>
      ) : null}

      {error.timestamp ? (
        <h2 className={cn("text-[1.5rem]")}>Timestamp: {error.timestamp}</h2>
      ) : null}
    </div>
  );
}
