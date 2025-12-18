import Layout from "../../components/Layout";
import { useCustomers } from "../../hooks/useCustomers";
import SearchBar from "../../components/SearchBar";
import Table from "../../components/Table";
import { useNavigate } from "react-router-dom";
import { useMemo, useState } from "react";

export default function Customer() {
  const [searchText, setSearchText] = useState("");
  const [filterStatus, setFilterStatus] = useState("");
  const { customers, error, isLoadingList, meta, links, fetchCustomers } =
    useCustomers({
      search: searchText,
      status: filterStatus,
    });
  const navigate = useNavigate();

  const filterOptions = [
    { label: "Terverifikasi", value: 1 },
    { label: "Terblokir", value: false },
  ];

  const filteredCustomers = useMemo(() => {
    return customers.filter((row) => {
      const nameMatch = row.full_name
        .toLowerCase()
        .includes(searchText.toLowerCase());

      const emailMatch = row.user.email
        .toLowerCase()
        .includes(searchText.toLowerCase());

      const phoneMatch = row.telephone.includes(searchText);

      return nameMatch || emailMatch || phoneMatch;
    });
  }, [customers, searchText]);

  const columns = [
    {
      label: "No",
      align: "center",
      render: (_, i) => (meta.current_page - 1) * meta.per_page + (i + 1),
    },
    { label: "Nama Customer", key: "full_name" },
    { label: "Email", render: (d) => d.user.email },
    { label: "NO. TLP", key: "telephone" },
    {
      label: "Status",
      render: (row) =>
        row.verified === 1 || row.verified === true ? (
          <span className="inline-flex items-center gap-x-1.5 rounded-full bg-green-100 px-2 py-1 text-xs font-medium text-green-800">
            <svg
              className="h-1.5 w-1.5 fill-green-500"
              viewBox="0 0 8 8"
              aria-hidden="true"
            >
              <circle cx="4" cy="4" r="3" />
            </svg>
            Terverifikasi
          </span>
        ) : (
          <span className="inline-flex items-center gap-x-1.5 rounded-full bg-red-100 px-2 py-1 text-xs font-medium text-red-800">
            <svg
              className="h-1.5 w-1.5 fill-red-500"
              viewBox="0 0 8 8"
              aria-hidden="true"
            >
              <circle cx="4" cy="4" r="3" />
            </svg>
            Terblokir
          </span>
        ),
    },
    {
      label: "Aksi",
      align: "center",
      render: (row) => (
        <button
          onClick={() => navigate(`/customer/${row.id}`)}
          className="text-green-600 hover:text-blue-green dark:text-green-400 dark:hover:text-green-300 font-semibold bg-green-200 rounded-2xl px-2 py-1 text-xs"
        >
          Detail
        </button>
      ),
    },
  ];
  return (
    <Layout>
      <SearchBar
        searchText={searchText}
        onSearchChange={setSearchText}
        filterOptions={filterOptions}
        filterValue={filterStatus}
        onFilterChange={setFilterStatus}
      />
      <div className="bg-white rounded-2xl mt-4 min-h-[78%]">
        <Table
          columns={columns}
          data={filteredCustomers}
          loading={isLoadingList}
          error={error}
        />
      </div>
      {/* Pagination */}
      {!isLoadingList && filteredCustomers.length > 0 && (
        <div className="mt-4">
          <button
            disabled={!links.prev_page_url}
            onClick={() => fetchCustomers(meta.current_page - 1)}
            className={`px-3 py-1 rounded-lg text-xs ${
              links.prev_page_url
                ? "bg-green-500 text-white hover:bg-green-600"
                : "bg-gray-200 text-gray-400 cursor-not-allowed"
            }`}
          >
            &laquo;
          </button>
          <span className="text-xs mx-2">
            Halaman {meta.current_page} dari {meta.last_page}
          </span>
          <button
            disabled={!links.next_page_url}
            onClick={() => fetchCustomers(meta.current_page + 1)}
            className={`px-3 py-1 rounded-lg text-xs ${
              links.next_page_url
                ? "bg-green-500 text-white hover:bg-green-600"
                : "bg-gray-200 text-gray-400 cursor-not-allowed"
            }`}
          >
            &raquo;
          </button>
        </div>
      )}
    </Layout>
  );
}
