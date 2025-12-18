import Layout from "../../components/Layout";
import { useUsers } from "../../hooks/useUsers";
import SearchBar from "../../components/SearchBar";
import Table from "../../components/Table";
import { useNavigate } from "react-router-dom";
import { useState, useMemo } from "react";

export default function User() {
  const [searchText, setSearchText] = useState("");
  const [filterStatus, setFilterStatus] = useState("");
  const roles = useMemo(() => ["driver", "customer"], []);

  const { users, error, isLoadingList, meta, links, fetchUsers } = useUsers({
    search: searchText,
    status: filterStatus,
    role: roles,
  });
  const navigate = useNavigate();

  const filterOptions = [
    { label: "Terblokir", value: 1 },
    { label: "Akses", value: 0 },
  ];

  const columns = [
    {
      label: "No",
      align: "center",
      render: (_, i) => (meta.current_page - 1) * meta.per_page + (i + 1),
    },
    { label: "Nama User", key: "name" },
    { label: "Email", key: "email" },
    { label: "Role", key: "user_type" },
    {
      label: "Status",
      render: (row) =>
        row.banned === 1 || row.banned === true ? (
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
        ) : (
          <span className="inline-flex items-center gap-x-1.5 rounded-full bg-green-100 px-2 py-1 text-xs font-medium text-green-800">
            <svg
              className="h-1.5 w-1.5 fill-green-500"
              viewBox="0 0 8 8"
              aria-hidden="true"
            >
              <circle cx="4" cy="4" r="3" />
            </svg>
            Akses
          </span>
        ),
    },
    {
      label: "Aksi",
      align: "center",
      render: (row) => (
        <button
          onClick={() => navigate(`/verifikasi/${row.id}`)}
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
          data={users}
          loading={isLoadingList}
          error={error}
        />
      </div>
      {/* Pagination */}
      {!isLoadingList && users.length > 0 && (
        <div className="mt-4">
          <button
            disabled={!links.prev_page_url}
            onClick={() => fetchUsers(meta.current_page - 1)}
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
            onClick={() => fetchUsers(meta.current_page + 1)}
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
