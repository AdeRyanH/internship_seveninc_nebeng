import { useParams } from "react-router-dom";
import Layout from "../../components/Layout";
import { useCustomers } from "../../hooks/useCustomers";
import Input from "../../components/Input";
import dayjs from "dayjs";
import Swal from "sweetalert2";
import { useState, useEffect } from "react";

export default function DetailCustomer() {
  const { id } = useParams();
  const { getCustomerById, verifyCustomer, isLoadingDetail } = useCustomers();
  const formatTanggal = (tanggal) =>
    tanggal ? dayjs(tanggal).format("DD MMMM YYYY") : "-";

  const [customer, setCustomer] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await getCustomerById(id);
        setCustomer(res.data);
      } catch (err) {
        setError(err.message);
      }
    };

    load();
  }, [id, getCustomerById]);

  const handleVerify = async (status) => {
    if (!customer) return;
    try {
      await verifyCustomer(customer.id, status);
      Swal.fire(
        status === true ? "Terverifikasi" : "Diblokir",
        `Customer berhasil ${status === true ? "diverifikasi" : "diblikir"}!`,
        "success"
      );
    } catch (error) {
      Swal.fire("Gagal", "Terjadi kesalahan, coba lagi", "error");
      console.log(error);
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

  if (error || !customer)
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
              src={customer.profile_img || "https://placehold.co/200x200"}
              alt="SIM"
            />
          </div>
          <div className="flex flex-col justify-center justify-items-center m-3">
            <h1 className="font-semibold">{customer.full_name}</h1>
            <p className="text-gray-600">{customer.user?.email}</p>
            <p className="text-gray-600">{customer.telephone}</p>
            <span>
              {customer.verified === true ? (
                <span className="inline-flex items-center gap-x-1.5 rounded-full bg-green-800 px-2 py-1 text-xs font-medium text-white">
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
                <span className="inline-flex items-center gap-x-1.5 rounded-full bg-red-800 px-2 py-1 text-xs font-medium text-white">
                  <svg
                    className="h-1.5 w-1.5 fill-red-500"
                    viewBox="0 0 8 8"
                    aria-hidden="true"
                  >
                    <circle cx="4" cy="4" r="3" />
                  </svg>
                  Terblokir
                </span>
              )}
            </span>
          </div>
        </div>
        <h1 className="m-3 font-bold">Informasi Pribadi</h1>
        <div className="m-3 flex justify-baseline w-full">
          {/* Sisi Kiri */}
          <div className="flex flex-col min-w-lg">
            <Input label="ID Customer" value={customer.id} />
            <Input label="Nama Lengkap" value={customer.full_name} />
            <Input label="Username" value={customer.user?.username} />
          </div>
          {/* Sisi Kanan */}
          <div className="flex flex-col">
            <Input label="Email" value={customer.user?.email} />
            <Input
              label="Tanggal Bergabung"
              value={formatTanggal(customer.user?.created_at)}
            />
            <Input label="No.Tlp" value={customer.telephone} />
          </div>
        </div>
        <br />
        <h1 className="m-3 font-bold">Informasi KTP</h1>
        <div className="m-3 flex justify-baseline w-full">
          {/* Sisi Kiri */}
          <div className="flex flex-col min-w-lg">
            <Input
              label="Nama Lengkap Sesuai KTP"
              value={customer.id_card_fullname}
            />
            <Input
              label="Tanggal Lahir"
              value={formatTanggal(customer.id_card_birthdate)}
            />
          </div>
          {/* Sisi Kanan */}
          <div className="flex flex-col">
            <Input label="NIK" value={customer.id_card_number} />
          </div>
        </div>
        <br />
        <div className="w-fi m-3 rounded-2xl min-h-fit flex">
          <div className="rounded-2xl p-3 flex-col">
            <p className="font-semibold text-center">Foto Wajah</p>
            <img
              src={customer.face_img || "https://placehold.co/300x200"}
              alt="face img"
            />
          </div>
          <div className="rounded-2xl p-3 flex-col">
            <p className="font-semibold text-center">Foto Wajah Dengan KTP</p>
            <img
              src={
                customer.face_with_id_image || "https://placehold.co/300x200"
              }
              alt="face with ktp"
            />
          </div>
          <div className="rounded-2xl p-3 flex-col">
            <p className="font-semibold text-center">Foto KTP</p>
            <img
              src={customer.id_card_img || "https://placehold.co/300x200"}
              alt="ktp"
            />
          </div>
        </div>
        <div className="p-3">
          <button
            className="bg-green-500 text-white px-3 py-2 rounded-xl mr-2"
            onClick={async () => {
              const result = await Swal.fire({
                title: "Konfirmasi",
                text: "Apakah kamu yakin ingin verifikasi customer ini?",
                icon: "question",
                showCancelButton: true,
                confirmButtonText: "Ya, Terima",
                cancelButtonText: "Batal",
              });
              if (result.isConfirmed) {
                handleVerify(true);
              }
            }}
            disabled={isLoadingDetail}
          >
            Verifikasi Customer
          </button>
          <button
            className="bg-red-500 text-white px-3 py-2 rounded-xl"
            onClick={async () => {
              const result = await Swal.fire({
                title: "Konfirmasi",
                text: "Apakah kamu yakin ingin memblokir customer ini?",
                icon: "question",
                showCancelButton: true,
                confirmButtonText: "Ya, Terima",
                cancelButtonText: "Batal",
              });
              if (result.isConfirmed) {
                handleVerify(false);
              }
            }}
            disabled={isLoadingDetail}
          >
            Blokir Customer
          </button>
        </div>
      </div>
    </Layout>
  );
}
