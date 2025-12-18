import Layout from "../../components/Layout";
import { useVehicles } from "../../hooks/useVehicles";
import SearchBar from "../../components/SearchBar";
import Table from "../../components/Table";
import { useNavigate } from "react-router-dom";
import { useMemo, useState } from "react";

export default function Vehicle() {
  const [searchText, setSearchText] = useState("");
  const [filterStatus, setFilterStatus] = useState("");
  const { vehicles, error, isLoadingList, meta, links, fetchVehicles } =
    useVehicles({
      search: searchText,
      status: filterStatus,
    });
  const navigate = useNavigate();

  const filterOptions = [
    { label: "Terblokir", value: 1 },
    { label: "Akses", value: 0 },
  ];

  const filteredVehicles = useMemo(() => {
    return vehicles.filter((row) => {
      const nameMatch = row.vehicle_name
        .toLowerCase()
        .includes(searchText.toLowerCase());

      const driverMatch = row.driver.full_name
        .toLowerCase()
        .includes(searchText.toLowerCase());

      const typeMatch = row.vehicle_type
        .toLowerCase()
        .includes(searchText.toLowerCase());

      const regisMatch = row.registration_number
        .toLowerCase()
        .includes(searchText.toLowerCase());

      return nameMatch || driverMatch || typeMatch || regisMatch;
    });
  }, [vehicles, searchText]);

  const columns = [
    { label: "Kendaraan", key: "vehicle_name" },
    { label: "Nama Driver", render: (row) => row.driver?.full_name || "-" },
    { label: "Plat Nomor", key: "registration_number" },
    { label: "Warna", key: "vehicle_color" },
    {
      label: "Aksi",
      align: "center",
      render: (row) => (
        <button
          onClick={() => navigate(`/kendaraan/${row.id}`)}
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
          data={filteredVehicles}
          loading={isLoadingList}
          error={error}
        />
      </div>
      {/* Pagination */}
      {!isLoadingList && filteredVehicles.length > 0 && (
        <div className="mt-4">
          <button
            disabled={!links.prev_page_url}
            onClick={() => fetchVehicles(meta.current_page - 1)}
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
            onClick={() => fetchVehicles(meta.current_page + 1)}
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
