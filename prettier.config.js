/** @type {import("prettier").Options} */
const base_options = {
  arrowParens: "always",
  bracketSameLine: true,
  bracketSpacing: true,
  endOfLine: "lf",
  htmlWhitespaceSensitivity: "strict",
  jsxSingleQuote: false,
  printWidth: 80,
  proseWrap: "always",
  quoteProps: "as-needed",
  semi: true,
  singleAttributePerLine: false,
  singleQuote: false,
  tabWidth: 2,
  trailingComma: "es5",
  useTabs: false,
  vueIndentScriptAndStyle: true,
};

/** @type {import("prettier").Config} */
export default {
  overrides: [
    {
      files: "*.html",
      options: {
        ...base_options,
        attributeSort: "ASC",
        parser: "html",
        plugins: [
          "prettier-plugin-organize-attributes",
          "prettier-plugin-tailwindcss",
        ],
      },
    },
    {
      files: "*.java",
      options: {
        ...base_options,
        parser: "java",
        plugins: ["prettier-plugin-java"],
      },
    },
    {
      files: "*.js",
      options: {
        ...base_options,
        parser: "babel",
        plugins: [
          "prettier-plugin-sort-imports",
          "prettier-plugin-sort-members",
        ],
      },
    },
    {
      files: "*.json",
      options: {
        ...base_options,
        parser: "json",
        plugins: ["prettier-plugin-sort-json"],
      },
    },
    {
      files: "*.ts",
      options: {
        ...base_options,
        parser: "typescript",
        plugins: [
          "prettier-plugin-sort-imports",
          "prettier-plugin-sort-members",
        ],
      },
    },
    {
      files: "*.tsx",
      options: {
        ...base_options,
        parser: "typescript",
        plugins: [
          "prettier-plugin-sort-imports",
          "prettier-plugin-sort-members",
          "prettier-plugin-tailwindcss",
        ],
      },
    },
    {
      files: "*.xml",
      options: {
        ...base_options,
        parser: "xml",
        plugins: ["@prettier/plugin-xml"],
      },
    },
    {
      files: "*.yaml",
      options: {
        ...base_options,
        parser: "yaml",
        plugins: [],
      },
    },
  ],
};
