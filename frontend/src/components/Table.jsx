export default function Table({ columns, data, loading, error }) {
  console.log("Table render", { dataLength: data.length });
  return (
    <table className="min-w-full divide-y divide-gray-200 dark:divide-neutral-700 overflow-hidden">
      <thead>
        <tr>
          {columns.map((col, i) => (
            <th
              key={i}
              scope="col"
              className={`px-6 py-3 text-xs font-medium uppercase ${
                col.align === "center"
                  ? "text-center"
                  : col.align === "right"
                    ? "text-right"
                    : "text-left"
              } text-gray-500 dark:text-black`}
            >
              {col.label}
            </th>
          ))}
        </tr>
      </thead>
      <tbody className="divide-y divide-gray-200 dark:divide-neutral-700">
        {loading ? (
          <tr>
            <td
              colSpan={columns.length}
              className="text-center py-4 text-gray-500 dark:text-neutral-400"
            >
              <svg
                className="w-6 h-6 mx-auto text-gray-500 animate-spin"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
              >
                <circle
                  className="opacity-25"
                  cx="12"
                  cy="12"
                  r="10"
                  stroke="currentColor"
                  strokeWidth="4"
                ></circle>
                <path
                  className="opacity-75"
                  fill="currentColor"
                  d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"
                ></path>
              </svg>
            </td>
          </tr>
        ) : error ? (
          <tr>
            <td
              colSpan={columns.length}
              className="text-center py-4 text-gray-500 dark:text-neutral-400"
            >
              Terjadi Kesalahan saat memuat data
              <br />
              <span className="text-sm text-gray-400">
                {error.message || "Coba lagi nanti"}
              </span>
            </td>
          </tr>
        ) : data.length === 0 ? (
          <tr>
            <td
              colSpan="7"
              className="text-center py-4 text-gray-500 dark:text-neutral-400"
            >
              Tidak ada data
            </td>
          </tr>
        ) : (
          data.map((row, i) => (
            <tr key={row.booking_code ? row.booking_code : row.id || i}>
              {columns.map((col, j) => (
                <td
                  key={j}
                  className={`relative px-6 py-4 whitespace-nowrap text-sm font-medium ${
                    col.align === "center"
                      ? "text-center"
                      : col.align === "right"
                        ? "text-right"
                        : "text-left"
                  } text-gray-800 dark:text-black`}
                >
                  {typeof col.render === "function"
                    ? col.render(row, i)
                    : row[col.key]}
                </td>
              ))}
            </tr>
          ))
        )}
      </tbody>
    </table>
  );
}
