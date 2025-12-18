export default function SearchBar({
  searchText,
  onSearchChange,
  filterOptions = [],
  filterValue,
  onFilterChange,
}) {
  return (
    <div className=" flex items-center justify-between bg-white min-h-fit p-3 rounded-2xl">
      <div className="w-md space-y-3 border-1 border-gray-300 rounded-xl">
        <input
          type="text"
          className="py-2.5 sm:py-3 px-4 block w-full border-gray-200 rounded-xl sm:text-sm focus:border-blue-500 focus:ring-blue-500 disabled:opacity-50 disabled:pointer-events-none dark:bg-gray-400 dark:border-neutral-700 dark:text-white dark:placeholder-white dark:focus:ring-black"
          placeholder="Cari disini"
          value={searchText}
          onChange={(e) => onSearchChange(e.target.value)}
        />
      </div>
      <div className="flex">
        <select
          className="bg-gray-100 shadow-sm p-1.5 rounded-lg dark:hover:bg-gray-500 dark:text-white dark:bg-gray-400"
          value={filterValue}
          onChange={(e) => onFilterChange(e.target.value)}
        >
          <option value="">Semua Status</option>
          {filterOptions.map((opt) => (
            <option key={opt.value} value={opt.value}>
              {opt.label}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
}
