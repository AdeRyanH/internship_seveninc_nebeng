import api from "../api/api";

function handleError(error) {
  if (error.response) {
    const msg = error.response.data?.message || "Terjadi kesalahan di server";
    console.error("Server error", msg);
    throw new Error(msg);
  } else if (error.request) {
    console.error("Tidak ada response dari server");
    throw new Error("Server tidak merespons, periksa koneksi anda");
  } else {
    console.error("Error", error.message);
    throw new Error("Terjadi kesalahan tidak diketahui");
  }
}

const rideService = {
  getAll: async ({ page = 1, search = "", status = "" }) => {
    try {
      const params = new URLSearchParams();

      params.append("page", page);

      if (search) params.append("search", search);
      if (status) params.append("status", status);

      const res = await api.get(`/api/rides?${params.toString()}`);
      // console.log("Data dari ride service :", res);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  getById: async (id, type) => {
    try {
      const res = await api.get(`/api/rides/${type}/${id}`);
      console.log("Data detail ride dari service : ", res);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },
};

export default rideService;
