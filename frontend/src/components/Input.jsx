export default function Input({
  value = "",
  type = "text",
  label = "",
  disabled = true,
  className = "w-md",
}) {
  return (
    <div className={className}>
      <label
        htmlFor="input-label"
        className="block text-sm font-medium mb-2 mt-3 dark:text-white"
      >
        {label}
      </label>
      <input
        type={type}
        value={value}
        disabled={disabled}
        className="py-2.5 sm:py-3 px-4 block w-full bg-gray-200 border-gray-400 rounded-lg sm:text-sm focus:border-blue-500 focus:ring-blue-500 disabled:opacity-50 disabled:pointer-events-none dark:bg-neutral-900 dark:border-neutral-700 dark:text-neutral-400 dark:placeholder-neutral-500 dark:focus:ring-neutral-600"
      />
    </div>
  );
}
