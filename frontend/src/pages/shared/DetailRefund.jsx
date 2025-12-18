import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Layout from "../../components/Layout";
import { useRefunds } from "../../hooks/useRefund";
import Input from "../../components/Input";
import dayjs from "dayjs";

export default function DetailPesanan() {
  const { type, booking_id } = useParams();
  console.log("TYPE dari params :", type);
  console.log("BOOKING_ID dari params :", booking_id);
  const { fetchRefundById, isLoadingDetail } = useRefunds();

  const formatTanggal = (tanggal) =>
    tanggal ? dayjs(tanggal).format("DD MMMM YYYY - HH:mm") : "-";

  const [refund, setRefund] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchRefundById(type, booking_id);
        console.log("type :", type);
        console.log("id :", booking_id);
        console.log(":", data);
        setRefund(data.data);
      } catch (err) {
        setError(err.message);
      }
    };

    load();
  }, [type, booking_id, fetchRefundById]);

  return (
    <Layout>
      {isLoadingDetail ? (
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
      ) : error ? (
        <h1 className="text-center py-4 text-gray-500 dark:text-neutral-400">
          Terjadi Kesalahan saat memuat data
          <br />
          <span className="text-sm text-gray-400">
            {error.message || "Coba lagi nanti"}
          </span>
        </h1>
      ) : !refund ? (
        <h1 className="text-center py-4 text-gray-500 dark:text-neutral-400">
          Tidak ada data
        </h1>
      ) : (
        <>
          <div className="bg-white rounded-xl p-3 mb-2 flex justify-between text-black">
            <h1 className="">ID PESANAN :</h1>
            <h1 className="">{refund?.booking_code}</h1>
          </div>
          <div className="flex flex-row justify-between w-full gap-4">
            {/* Customer */}
            <div className="bg-white rounded-2xl p-3 my-3 w-full">
              <h1 className="font-bold text-xl text-gray-400 p-3">
                Informasi Customer
              </h1>
              <div className="flex flex-row">
                <div className="p-3">
                  <img
                    className="rounded-sm"
                    src={
                      refund?.customer?.profile_img ||
                      "https://placehold.co/200x200"
                    }
                    alt="profile"
                  />
                </div>
                <div className="">
                  <Input
                    label="Nama Lengkap"
                    value={refund?.customer?.full_name ?? "-"}
                  />
                  <Input
                    label="No Tlp"
                    value={refund?.customer?.telephone ?? "-"}
                  />
                </div>
              </div>
            </div>
            {/* Rincian Perjalanan */}
            <div className="flex flex-row w-full">
              <div className="bg-white rounded-2xl p-3 my-3 w-full">
                <div className="flex flex-col">
                  <h1 className="font-bold text-xl text-gray-400 p-3">
                    Rincian Perjalanan
                  </h1>
                  <div className="flex justify-between p-3">
                    <h2>{formatTanggal(refund?.created_at)}</h2>
                  </div>
                  <hr className="mb-2" />
                  <div className="flex justify-between p-3">
                    <div className="flex flex-col">
                      <h1 className="font-semibold text-blue-600">
                        Titik Jemput
                      </h1>
                      <h2 className="font-semibold">
                        {refund?.passenger_ride?.kota_awal}
                      </h2>
                      <h2 className="text-sm">
                        {refund?.terminal_keberangkatan}
                      </h2>
                    </div>
                    <div className="flex flex-col">
                      <h1 className="font-semibold text-blue-600">Tujuan</h1>
                      <h2 className="font-semibold">{refund?.kota_tujuan}</h2>
                      <h2 className="text-sm">{refund?.terminal_tujuan}</h2>
                    </div>
                  </div>
                  <div className="flex"></div>
                </div>
              </div>
            </div>
          </div>
          <div className="flex flex-row justify-between gap-4 w-full">
            {/* Mitra */}
            <div className="bg-white rounded-2xl p-3 w-full">
              <h1 className="font-bold text-xl text-gray-400 p-3">
                Informasi Driver
              </h1>
              <div className="flex flex-row">
                <div className="p-3">
                  <img
                    className="rounded-sm"
                    src={
                      refund?.customer?.profile_img ||
                      "https://placehold.co/200x200"
                    }
                    alt="profile"
                  />
                </div>
                <div className="">
                  <Input
                    label="Nama Lengkap"
                    value={refund?.passenger_ride?.driver?.full_name ?? "-"}
                  />
                  <Input
                    label="No Tlp"
                    value={refund?.passenger_ride?.driver?.telephone ?? "-"}
                  />
                  <Input
                    label="Kendaraan"
                    value={refund?.passenger_ride?.vehicle_type ?? "-"}
                  />
                  <Input label="No Kendaraan" />
                </div>
              </div>
            </div>
            <div className="bg-white p-6 w-full rounded-2xl">
              <h1 className="font-bold text-xl text-gray-400 pb-3">
                Informasi Pembayaran
              </h1>
              <div className="flex flex-col">
                <div className="flex justify-between pb-3">
                  <h1>Tipe Pembayaran</h1>
                  <h1>
                    {refund?.transaction?.data?.payment_method.bank_name ?? "-"}
                  </h1>
                </div>
                <div className="flex justify-between pb-3">
                  <h1>Tanggal</h1>
                  <h1>
                    {formatTanggal(refund?.transaction?.data?.transaction_date)}
                  </h1>
                </div>
                <hr className="py-3" />
                <div className="flex justify-between pb-3">
                  <h1>ID Pesanan</h1>
                  <h1>{refund?.booking_code ?? "-"}</h1>
                </div>
                <div className="flex justify-between pb-3">
                  <h1>No Transaksi</h1>
                  <h1>{refund?.transaction?.data?.transaction_code ?? "-"}</h1>
                </div>
                <hr className="py-3" />
                <div className="flex justify-between pb-3">
                  <h1>
                    {refund?.type === "Passenger"
                      ? "Biaya Per penebeng"
                      : refund?.type === "Goods"
                        ? "Biaya Per kg"
                        : "-"}
                  </h1>
                  <h1>
                    {refund?.type === "Passenger"
                      ? (refund?.passenger_ride?.price_per_seat ?? "-")
                      : refund?.type === "Goods"
                        ? (refund?.goods_ride?.price_per_kg ?? "-")
                        : "-"}
                  </h1>
                </div>
                <div className="flex justify-between pb-3">
                  <h1>Sub Total</h1>
                  <h1>Tanggal</h1>
                </div>
                <hr className="py-3" />
                <div className="flex justify-between pb-3">
                  <h1>Total</h1>
                  <h1>{refund?.total_price ?? "-"}</h1>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
    </Layout>
  );
}
