import { useParams } from "react-router-dom";
import Layout from "../../components/Layout";
import { useVehicles } from "../../hooks/useVehicles";
import { useEffect, useState } from "react";
import dayjs from "dayjs";
import Input from "../../components/Input";
import Swal from "sweetalert2";

export default function DetailKendaraan() {
  const { id } = useParams();
  const {
    getVehicleById,
    isLoadingDetail,
    isLoadingAction,
    getDriver,
    verifyVehicle,
    rejectVehicle,
  } = useVehicles();

  const [vehicle, setVehicle] = useState(null);
  const [error, setError] = useState(null);
  const [driver, setDriver] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await getVehicleById(id);
        setVehicle(res.data);
      } catch (error) {
        setError(error);
      }
    };

    load();
  }, [id, getVehicleById]);

  console.log("Driver id di page: ", vehicle?.driver_id);

  useEffect(() => {
    if (!vehicle || !vehicle.driver_id) return;
    const loadDriver = async () => {
      try {
        const res = await getDriver(vehicle.driver_id);
        setDriver(res.data);
      } catch (error) {
        setError(error);
      }
    };

    loadDriver();
  }, [vehicle, getDriver]);

  const formatTanggal = (tanggal) =>
    tanggal ? dayjs(tanggal).format("DD MMMM YYYY") : "-";

  const handleVerify = async () => {
    if (!vehicle) return;
    try {
      await verifyVehicle(vehicle.id);

      const refreshed = await getVehicleById(vehicle.id);
      setVehicle(refreshed.data);

      Swal.fire("Sukses", "Kendaraan berhasil diverifikasi", "success");
    } catch (error) {
      Swal.fire("Gagal", "Terjadi kesalahan, coba lagi", "error");
      console.log(error);
    }
  };

  const handleReject = async (reason) => {
    if (!vehicle) return;

    const result = await Swal.fire({
      title: "Masukkan alasan penolakan",
      input: "text",
      inputPlaceholder: "Alasan penolakan ...",
      showCancelButton: true,
      confirmButtonText: "Kirim",
      cancelButtonText: "Batal",
      inputValidator: (value) => {
        if (!value) return "Alsan wajib diisi";
        if (value.length > 255) return "Alasan terlalu panjang";
        return null;
      },
    });
    if (!result.isConfirmed || !result.value) return;
    reason = result.value;

    try {
      await rejectVehicle(vehicle.id, reason);

      const refreshed = await getVehicleById(vehicle.id);
      setVehicle(refreshed.data);

      Swal.fire("Sukses", "Kendaraan berhasil di tolak", "success");
    } catch (error) {
      console.error("Reject error :", error);
      Swal.fire("Gagal", "Terjadi kesalahan saat menolak kendaraan", "error");
    }
  };

  if (isLoadingDetail)
    return (
      <Layout>
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
      </Layout>
    );

  if (error || !vehicle)
    return (
      <Layout>
        <h1 className="text-center py-4 text-gray-500 dark:text-neutral-400">
          {error ? "Terjadi Kesalahan saat memuat data" : "Belum ada data"}
          <br />
          <span className="text-sm text-gray-400">
            {error ? error.message : "Coba lagi nanti"}
          </span>
        </h1>
      </Layout>
    );

  return (
    <Layout>
      <div className="bg-white rounded-2xl min-h-screen p-3">
        <div className="bg-gray-200 min-h-fit w-full rounded-2xl flex justify-items-center">
          <div className="m-5">
            <img
              className="rounded-full"
              src={driver?.profile_img || "https://placehold.co/200x200"}
              alt="SIM"
            />
          </div>
          <div className="flex flex-col justify-center justify-items-center m-3">
            <h1 className="font-semibold">{vehicle?.driver?.full_name}</h1>
            <p className="text-gray-600">{vehicle?.driver?.user?.email}</p>
            <p className="text-gray-600">{vehicle?.driver?.telephone}</p>
            <span>
              {vehicle.vehicle_verified === true ? (
                <span className="inline-flex items-center gap-x-1.5 rounded-full bg-green-800 px-2 py-1 text-xs font-medium text-white">
                  <svg
                    className="h-1.5 w-1.5 fill-green-500"
                    viewBox="0 0 8 8"
                    aria-hidden="true"
                  >
                    <circle cx="4" cy="4" r="3" />
                  </svg>
                  Kendaraan Terverifikasi
                </span>
              ) : (
                <span className="inline-flex items-center gap-x-1.5 rounded-full bg-red-800 px-2 py-1 text-xs font-medium text-white">
                  <svg
                    className="h-1.5 w-1.5 fill-red-500"
                    viewBox="0 0 8 8"
                    aria-hidden="true"
                  >
                    <circle cx="4" cy="4" r="3" />
                  </svg>
                  Kendaraan Terblokir
                </span>
              )}
            </span>
          </div>
        </div>
        <h1 className="m-3 font-bold pt-3">Informasi Pribadi</h1>
        <div className="m-3 flex justify-baseline w-full">
          {/* Sisi Kiri */}
          <div className="flex flex-col min-w-lg">
            <Input label="Nama Lengkap" value={vehicle?.driver?.full_name} />
            <Input label="Kendaraan" value={vehicle?.vehicle_name} />
            <Input label="Warna Kendaraan" value={vehicle?.vehicle_color} />
            <Input label="Type Kendaraan" value={vehicle?.vehicle_type} />
            <Input
              label="Plat Kendaraan"
              value={vehicle?.registration_number}
            />
          </div>
          {/* Sisi Kanan */}
          <div className="flex flex-col justify-center items-center">
            <img
              src={vehicle?.vehicle_img || "https://placehold.co/300x200"}
              alt="kendaraan"
            />
          </div>
        </div>
        <br />
        <h1 className="m-3 font-bold">Informasi STNK</h1>
        <div className="m-3 flex justify-baseline w-full pb-3">
          {/* Sisi Kiri */}
          <div className="flex flex-col min-w-lg">
            <Input label="Tahun Kendaraan" value={vehicle?.registration_year} />
            <Input
              label="Berlaku Hingga"
              value={formatTanggal(vehicle?.registration_expired)}
            />
            <Input
              label="Status"
              value={
                vehicle?.vehicle_verified === true
                  ? "Terverifikasi"
                  : "Terblokir"
              }
            />
            {vehicle?.vehicle_verified === false && (
              <Input
                label="Alasan Blokir"
                value={vehicle?.vehicle_rejected_reason ?? "-"}
              />
            )}
          </div>
          {/* Sisi Kanan */}
          <div className="flex flex-col justify-center items-center">
            <img
              src={vehicle?.registration_img || "https://placehold.co/300x200"}
              alt="stnk"
            />
          </div>
        </div>
        <button
          className="bg-green-500 text-white px-3 py-2 rounded-xl mr-2"
          onClick={async () => {
            const result = await Swal.fire({
              title: "Konfirmasi",
              text: "Apakah kamu yakin ingin menerima kendaraan ini?",
              icon: "question",
              showCancelButton: true,
              confirmButtonText: "Ya, Terima",
              cancelButtonText: "Batal",
            });

            if (result.isConfirmed) {
              handleVerify();
            }
          }}
          disabled={isLoadingAction}
        >
          Terima
        </button>
        <button
          className="bg-red-500 text-white px-3 py-2 rounded-xl"
          onClick={() => handleReject()}
          disabled={isLoadingAction}
        >
          Tolak
        </button>
        <div className=""></div>
      </div>
    </Layout>
  );
}
