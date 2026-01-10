import Layout from "../../components/Layout";
import { useNavigate } from "react-router-dom";
import dayjs from "dayjs";
import { useRefunds } from "../../hooks/useRefund";
import Table from "../../components/Table";
import { useState, useCallback } from "react";
import SearchBar from "../../components/SearchBar";

export default function Refund() {
  const [searchText, setSearchText] = useState("");
  const [filterStatus, setFilterStatus] = useState("");
  const { refunds, isLoadingList, error, links, meta, fetchRefunds } =
    useRefunds({
      search: searchText,
      status: filterStatus,
    });
  const navigate = useNavigate();

  const filterOptions = [
    { label: "Proses", value: "proses" },
    { label: "Selesai", value: "selesai" },
    { label: "Batal", value: "batal" },
  ];

  const formatTanggal = useCallback(
    (tanggal) => (tanggal ? dayjs(tanggal).format("DD MMMM YYYY") : "-"),
    []
  );

  const renderAction = useCallback(
    (row) => (
      <button
        onClick={() => navigate(`/refund/${row.refund_type}/${row.id}`)}
        className="text-green-600 hover:text-blue-green dark:text-green-400 dark:hover:text-green-300 font-semibold bg-green-200 rounded-2xl px-2 py-1 text-xs"
      >
        Detail
      </button>
    ),
    [navigate]
  );

  function renderStatus(status) {
    const val = String(status).toLowerCase();

    if (val === "proses") {
      return (
        <span className="inline-flex items-center gap-x-1.5 rounded-full bg-yellow-100 px-2 py-1 text-xs font-medium text-yellow-800">
          Pending
        </span>
      );
    }

    if (val === "selesai") {
      return (
        <span className="inline-flex items-center gap-x-1.5 rounded-full bg-green-100 px-2 py-1 text-xs font-medium text-green-800">
          Diterima
        </span>
      );
    }
    if (val === "batal") {
      return (
        <span className="inline-flex items-center gap-x-1.5 rounded-full bg-red-100 px-2 py-1 text-xs font-medium text-red-800">
          Ditolak
        </span>
      );
    }
  }

  // render columns
  const renderTanggal = useCallback(
    (row) => formatTanggal(row.created_at),
    [formatTanggal]
  );

  // render customer
  const renderCustomer = useCallback(
    (row) => row?.passenger_transaction?.transaction_code ?? "-",
    []
  );

  const columns = [
    {
      label: "NO ORDER",
      key: "booking_code",
      align: "left",
    },
    {
      label: "CUSTOMER",
      render: (row) => row?.customer?.full_name ?? "-",
    },
    {
      label: "TANGGAL",
      render: renderTanggal,
    },
    {
      label: "No Transaksi",
      render: renderCustomer,
    },
    {
      label: "Jumlah Refund",
      align: "center",
      key: "total_price",
    },
    {
      label: "Status",
      render: (row) => renderStatus(row.reject_status),
    },
    {
      label: "Aksi",
      align: "center",
      render: renderAction,
    },
  ];
  return (
    <Layout>
      <SearchBar
        searchText={searchText}
        onSearchChange={setSearchText}
        filterOptions={filterOptions}
        onFilterChange={setFilterStatus}
      />
      <div className="bg-white rounded-2xl w-full min-h-screen p-3">
        <Table
          columns={columns}
          data={refunds}
          loading={isLoadingList}
          error={error}
        ></Table>
      </div>
      {/* Pagination */}
      {!isLoadingList && refunds.length > 0 && (
        <div className="mt-4">
          <button
            disabled={!links.prev_page_url}
            onClick={() => fetchRefunds(meta.current_page - 1)}
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
            onClick={() => fetchRefunds(meta.current_page + 1)}
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
