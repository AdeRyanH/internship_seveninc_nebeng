import Layout from "../../components/Layout.jsx";
import { useDrivers } from "../../hooks/useDrivers.js";
import { useParams } from "react-router-dom";
import Input from "../../components/Input.jsx";
import Swal from "sweetalert2";
import { useEffect, useState } from "react";

export default function DetailMitra() {
  const { id } = useParams();
  const { getDriverById, verifyDriver, isLoadingDetail, isLoadingAction } =
    useDrivers();

  const [mitra, setMitra] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await getDriverById(id);
        setMitra(res.data);
      } catch (err) {
        setError(err.message);
      }
    };

    load();
  }, [id, getDriverById]);

  const handleVerify = async (type, status) => {
    let reason = null;

    if (status === 0) {
      const result = await Swal.fire({
        title: "Masukkan alasan penolakan",
        input: "text",
        inputPlaceholder: "Tuliskan alasan...",
        showCancelButton: true,
        confirmButtonText: "Kirim",
        cancelButtonText: "Batal",
      });
      if (!result.value) return;
      reason = result.value;
    }

    try {
      const res = await verifyDriver(mitra.id, { type, status, reason });

      Swal.fire(
        status ? "Sukses" : "Ditolak",
        `${type.toUpperCase()} berhasil ${status ? "Diverifikasi" : "ditolak"}`,
        "success"
      );

      if (res?.data) setMitra(res.data);
    } catch (err) {
      setError(err.message);
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

  if (error || !mitra)
    return (
      <Layout>
        <h1 className="text-center py-4 text-gray-500 dark:text-neutral-400">
          {error ? "Terjadi Kesalahan saat memuat data" : "Belum ada data"}
          <br />
          <span className="text-sm text-gray-400">
            {typeof error === "string" ? error : error?.message}
          </span>
        </h1>
      </Layout>
    );
  {
    /* ================================================================================================================================================================================================================================== */
  }
  {
    /* ================================================================================================================================================================================================================================== */
  }
  {
    /* ================================================================================================================================================================================================================================== */
  }

  // Fungsi helper label
  const getLabel = (key) => {
    const labelMap = {
      nama: "Nama Lengkap",
      tanggalLahir: "Tanggal Lahir",
      nik: "Nomor KTP",
      noSim: "Nomor SIM",
      tipe: "Tipe SIM",
      expired: "Masa Berlaku",
      noSKCK: "Nomor SKCK",
      status: "Status Verifikasi",
      reason: "Alasan Penolakan",
    };

    // fallback otomatis jika label belum terdaftar
    return labelMap[key] || key.replace(/_/g, " ").toUpperCase();
  };

  // Data Section

  const sections = [
    {
      title: "Informasi KTP",
      type: "id_card",
      data: {
        nama: mitra.id_card_fullname,
        tanggalLahir: mitra.id_card_birthdate,
        nik: mitra.id_card_number,
        status: mitra.id_card_verified,
        reason: mitra.id_card_rejected_reason,
        img: mitra.id_card_img,
      },
    },
    {
      title: "Informasi SIM",
      type: "driver_license",
      data: {
        noSim: mitra.driver_license_number,
        tipe: mitra.driver_license_type,
        expired: mitra.driver_license_expired,
        reason: mitra.vehicles.rejected_reason,
        img: mitra.vehicles.vehicles_image,
      },
    },
    {
      title: "Informasi SKCK",
      type: "police_clearance",
      data: {
        noSKCK: mitra.police_clearance_certificate_number,
        nama: mitra.police_clearance_certificate_fullname,
        expired: mitra.police_clearance_certificate_expired,
        status: mitra.police_clearance_verified,
        reason: mitra.police_clearance_rejected_reason,
        img: mitra.police_clearance_certificate_img,
      },
    },
  ];

  {
    /* ================================================================================================================================================================================================================================== */
  }
  {
    /* ================================================================================================================================================================================================================================== */
  }
  {
    /* ================================================================================================================================================================================================================================== */
  }

  return (
    <Layout>
      <div className="bg-white rounded-2xl min-h-screen p-3">
        <div className="bg-gray-200 min-h-fit w-full rounded-2xl flex">
          <div className="m-5">
            <img
              className="rounded-full"
              src={mitra.profile_img || "https://placehold.co/200x200"}
              alt="profile"
            />
          </div>
          <div className="flex flex-col justify-items-center justify-center">
            <h1 className="font-bold text-lg">{mitra.full_name}</h1>
            <p className="text-gray-600">{mitra.user?.email}</p>
            <p className="text-gray-600">{mitra.telephone}</p>
          </div>
        </div>
        <h1 className="m-3 font-bold">Informasi Pribadi</h1>
        <div className="flex flex-row w-full">
          {/* Sisi Kiri */}
          <div className="flex flex-col m-3">
            <Input label="ID Driver" value={mitra.id} />
            <Input label="Nama Lengkap" value={mitra.full_name} />
            <Input label="Username" value={mitra.user?.username} />
            <Input label="Credit Score" value={mitra.credit_score} />
            <Input label="Email" value={mitra.user?.email} />
            <Input label="Tanggal Bergabung" value={mitra.user?.created_at} />
            <Input label="No.Tlp" value={mitra.telephone} />
            <Input label="Rating" value={mitra.average_rating} />
          </div>
          {/* Sisi Kanan */}
          <div className="flex flex-col justify-center items-center w-full m-3">
            <div className="">
              <label htmlFor="">Foto Wajah</label>
              <div className="rounded-2xl">
                <img
                  src={mitra.face_img || "https://placehold.co/300x200"}
                  alt="Foto Wajah"
                />
              </div>
            </div>
            <div className="m-3">
              <label htmlFor="">Foto Wajah & KTP</label>
              <div className="rounded-2xl mt-2">
                <img
                  src={mitra.face_with_id_img || "https://placehold.co/300x200"}
                  alt="Foto Wajah & KTP"
                />
              </div>
            </div>
          </div>
        </div>
        <br />
        <hr />

        {/* ================================================================================================================================================================================================================================== */}
        {/* ================================================================================================================================================================================================================================== */}
        {/* ================================================================================================================================================================================================================================== */}

        {sections.map((item) => (
          <section key={item.type}>
            <hr />
            <h1 className="m-3 font-bold">{item.title}</h1>
            <div className="m-3 flex justify-baseline w-full">
              {/* Sisi Kiri */}
              <div className="flex flex-col min-w-lg">
                {Object.entries(item.data).map(
                  ([key, value]) =>
                    key !== "img" && (
                      <Input
                        key={key}
                        label={getLabel(key)}
                        value={
                          key === "status"
                            ? value == true
                              ? "Terverifikasi"
                              : "Belum Diverivikasi"
                            : value || "-"
                        }
                      />
                    )
                )}
              </div>
              {/* Sisi Kanan */}
              <div className="flex flex-col m-3">
                <div className="w-fit bg-gray-300 m-3 rounded-2xl min-h-fit">
                  <div className="rounded-2xl">
                    <img
                      src={item.data.img || "https://placehold.co/300x200"}
                      alt={item.type}
                    />
                  </div>
                </div>
                <div className="mt-2 px-3">
                  {item.data.status === null || item.data.status === "null" ? (
                    <p className="text-gray-500 italic">Belum diverifikasi.</p>
                  ) : item.data.status == true ? (
                    <p className="text-green-600 font-semibold">
                      Terverifikasi ✅
                    </p>
                  ) : item.data.status == false ? (
                    <div className="text-red-600">
                      <p className="font-semibold">Ditolak ❌</p>
                      {item.data.reason && (
                        <p className="text-sm mt-1">
                          <strong>Alasan Penolakan:</strong> {item.data.reason}
                        </p>
                      )}
                    </div>
                  ) : null}
                </div>
                <div className="p-3">
                  <button
                    className="bg-green-500 text-white px-3 py-2 rounded-xl mr-2"
                    onClick={async () => {
                      const result = await Swal.fire({
                        title: "Konfirmasi",
                        text: "Apakah kamu yakin ingin menerima dokumen ini?",
                        icon: "question",
                        showCancelButton: true,
                        confirmButtonText: "Ya, Terima",
                        cancelButtonText: "Batal",
                      });

                      if (result.isConfirmed) {
                        handleVerify(item.type, 1);
                      }
                    }}
                    disabled={isLoadingAction}
                  >
                    Terima
                  </button>
                  <button
                    className="bg-red-500 text-white px-3 py-2 rounded-xl"
                    onClick={() => handleVerify(item.type, 0)}
                    disabled={isLoadingAction}
                  >
                    Tolak
                  </button>
                </div>
              </div>
            </div>
          </section>
        ))}
      </div>
    </Layout>
  );
}
