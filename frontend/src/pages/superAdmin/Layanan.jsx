import Layout from "../../components/Layout";
import { useRides } from "../../hooks/useRide";
import SearchBar from "../../components/SearchBar";
import Table from "../../components/Table";
import { useNavigate } from "react-router-dom";
import { useCallback, useState, useMemo } from "react";
import dayjs from "dayjs";

export default function Layanan() {
  const [searchText, setSearchText] = useState("");
  const [filterStatus, setFilterStatus] = useState("");
  const { rides, error, isLoadingList, meta, links, fetchRides } = useRides({
    search: searchText,
    status: filterStatus,
  });
  const navigate = useNavigate();

  const filterOptions = [
    { label: "Pending", value: "Pending" },
    { label: "Dalam Perjalanan", value: "Dalam Perjalanan" },
    { label: "Selesai", value: "Selesai" },
    { label: "Dibatalkan", value: "Dibatalkan" },
  ];

  // ⭐ FILTER FE (STATUS )
  const filteredLayanan = useMemo(() => {
    return rides.filter((row) => {
      // FE Filter Status
      const status = row.ride_status;

      let rowStatus = "";
      if (status === "Pending") rowStatus = "Pending";
      else if (status === "Dalam Perjalanan") rowStatus = "Dalam Perjalanan";
      else if (status === "Selesai") rowStatus = "Selesai";
      else if (status === "Dibatalkan") rowStatus = "Dibatalkan";

      if (filterStatus !== "" && rowStatus !== filterStatus) {
        return false;
      }

      return true;
    });
  }, [rides, filterStatus]);

  const formatTanggal = useCallback(
    (tanggal) => (tanggal ? dayjs(tanggal).format("DD MMMM YYYY") : "-"),
    []
  );

  const renderTanggal = useCallback(
    (row) => formatTanggal(row.departure_time),
    [formatTanggal]
  );

  const renderDriver = (row) => row.driver.full_name;

  const columns = [
    {
      label: "No",
      align: "center",
      render: (_, i) => (meta.current_page - 1) * meta.per_page + (i + 1),
    },
    { label: "Tanggal", render: renderTanggal },
    { label: "Nama Driver", render: renderDriver },
    { label: "Layanan", key: "ride_type" },
    {
      label: "Status",
      render: (row) => {
        const status = row.ride_status;

        if (status === "Pending") {
          return (
            <span className="inline-flex items-center gap-x-1.5 rounded-full bg-gray-100 px-2 py-1 text-xs font-medium text-gray-800">
              <svg
                className="h-1.5 w-1.5 fill-gray-500"
                viewBox="0 0 8 8"
                aria-hidden="true"
              >
                <circle cx="4" cy="4" r="3" />
              </svg>
              Pending
            </span>
          );
        }

        if (status === "Dalam Perjalanan") {
          return (
            <span className="inline-flex items-center gap-x-1.5 rounded-full bg-blue-100 px-2 py-1 text-xs font-medium text-blue-800">
              <svg
                className="h-1.5 w-1.5 fill-blue-500"
                viewBox="0 0 8 8"
                aria-hidden="true"
              >
                <circle cx="4" cy="4" r="3" />
              </svg>
              Dalam Perjalanan
            </span>
          );
        }

        if (status === "Dibatalkan") {
          return (
            <span className="inline-flex items-center gap-x-1.5 rounded-full bg-red-200 px-2 py-1 text-xs font-medium text-red-700">
              <svg
                className="h-1.5 w-1.5 fill-red-500"
                viewBox="0 0 8 8"
                aria-hidden="true"
              >
                <circle cx="4" cy="4" r="3" />
              </svg>
              Dibatalkan
            </span>
          );
        }

        if (status === "Selesai") {
          return (
            <span className="inline-flex items-center gap-x-1.5 rounded-full bg-green-100 px-2 py-1 text-xs font-medium text-green-800">
              <svg
                className="h-1.5 w-1.5 fill-green-500"
                viewBox="0 0 8 8"
                aria-hidden="true"
              >
                <circle cx="4" cy="4" r="3" />
              </svg>
              Selesai
            </span>
          );
        }

        return null;
      },
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
          data={filteredLayanan}
          loading={isLoadingList}
          error={error}
        />
      </div>
      {/* Pagination */}
      {!isLoadingList && filteredLayanan.length > 0 && (
        <div className="mt-4">
          <button
            disabled={!links.prev_page_url}
            onClick={() => fetchRides(meta.current_page - 1)}
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
            onClick={() => fetchRides(meta.current_page + 1)}
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
