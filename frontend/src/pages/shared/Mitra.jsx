import Layout from "../../components/Layout.jsx";
import SearchBar from "../../components/SearchBar.jsx";
import { useDrivers } from "../../hooks/useDrivers.js";
import Table from "../../components/Table.jsx";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Mitra() {
  const [searchText, setSearchText] = useState("");
  const [filterStatus, setFilterStatus] = useState("");
  const { drivers, error, isLoadingList, meta, links, fetchDrivers } =
    useDrivers({
      search: searchText,
      status: filterStatus,
    });
  const navigate = useNavigate();

  const filterOptions = [
    { label: "Terverifikasi", value: "terverifikasi" },
    { label: "Pengajuan", value: "pengajuan" },
    { label: "Terblokir", value: "terblokir" },
  ];

  const columns = [
    {
      label: "No",
      align: "center",
      render: (_, i) => (meta.current_page - 1) * meta.per_page + (i + 1),
    },
    { label: "Nama Mitra", key: "full_name" },
    { label: "Email", render: (d) => d.user.email },
    { label: "NO. TLP", key: "telephone" },
    { label: "Rating", align: "center", key: "average_rating" },
    {
      label: "Status",
      render: (row) => {
        const idCard = row.id_card?.verified ? true : false;
        const driverLicense = row.driver_license?.verified ? true : false;
        const police = row.police_certificate?.verified ? true : false;

        const allVerified =
          idCard === true && driverLicense === true && police === true;
        const anyVerified =
          idCard === true || driverLicense === true || police === true;

        if (allVerified) {
          return (
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
          );
        } else if (anyVerified) {
          return (
            <span className="inline-flex items-center gap-x-1.5 rounded-full bg-amber-100 px-2 py-1 text-xs font-medium text-amber-500">
              <svg
                className="h-1.5 w-1.5 fill-amber-500"
                viewBox="0 0 8 8"
                aria-hidden="true"
              >
                <circle cx="4" cy="4" r="3" />
              </svg>
              Pengajuan
            </span>
          );
        } else {
          return (
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
          );
        }
      },
    },
    {
      label: "Aksi",
      align: "center",
      render: (row) => (
        <button
          onClick={() => navigate(`/mitra/${row.id}`)}
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
      <div className="bg-white rounded-2xl mt-4 min-h-7/9">
        <Table
          columns={columns}
          data={drivers}
          loading={isLoadingList}
          error={error?.message}
        />
      </div>
      {/* Pagination */}
      {!isLoadingList && drivers.length > 0 && (
        <div className="mt-4">
          <button
            disabled={!links.prev_page_url}
            onClick={() => fetchDrivers(meta.current_page - 1)}
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
            onClick={() => fetchDrivers(meta.current_page + 1)}
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
