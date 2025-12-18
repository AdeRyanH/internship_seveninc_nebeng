import api from "../api/api";

function handleError(error) {
  if (error.response) {
    const msg = error.response.data?.message || "Terjadi kesalahan di server";
    console.error("Server Error", msg);
    throw new Error(msg);
  } else if (error.request) {
    console.error("Tidak ada response dari server");
    throw new Error("Server tidak merespon. Periksa koneksi anda");
  } else {
    console.error("Error:", error.message);
    throw new Error("Terjadi kesalahan tidak diketahui");
  }
}

const vehicleService = {
  getAll: async ({ page = 1, search = "", status = "" }) => {
    try {
      const params = new URLSearchParams();

      params.append("page", page);

      if (search) params.append("search", search);
      if (status) params.append("status", status);

      const res = await api.get(`/api/admin/vehicles?${params.toString()}`);
      console.log("Data dari vehicle service :", res);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  getById: async (id) => {
    try {
      const res = await api.get(`/api/admin/vehicles/${id}`);
      console.log("Data detail customer dari service : ", res);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  getDriver: async (driverId) => {
    try {
      const res = await api.get(`/api/admin/vehicles/getDriver/${driverId}`);
      console.log("Data driver dari service: ", res);
      return res.data;
    } catch (error) {
      handleError(error);
    }
  },

  create: async (data) => {
    try {
      const res = await api.post("/api/admin/vehicles", data);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  update: async (id, data) => {
    try {
      const res = await api.put(`/api/admin/vehicles/${id}`, data);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  verify: async (id) => {
    try {
      const res = await api.patch(`/api/admin/vehicles/${id}/verify`);
      console.log("📥 Verify vehicle di service:", res.data);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  reject: async (id, reason) => {
    try {
      const res = await api.patch(`/api/admin/vehicles/${id}/reject`, {
        reason,
      });
      console.log("📥 Reject vehicle di service:", res.data);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  delete: async (id) => {
    try {
      const res = await api.delete(`/api/admin/vehicles/${id}`);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },
};

export default vehicleService;
