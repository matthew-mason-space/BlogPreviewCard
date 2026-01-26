interface ImportMetaEnv {
  readonly VITE_API: string;
  readonly VITE_API_STATIC_RESOURCES: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
