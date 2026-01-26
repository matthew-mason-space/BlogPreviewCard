export type BlogResponseError = {
  error:
    | string
    | {
        details: string[];
      };
  message: string;
  path: string;
  status: number;
  timestamp: string;
};
