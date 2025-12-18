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
    throw new Error("Teradi kesalahan tidak diketahui");
  }
}

const driverService = {
  getAll: async ({ page = 1, search = "", status = "" }) => {
    try {
      const params = new URLSearchParams();

      params.append("page", page);

      if (search) params.append("search", search);
      if (status) params.append("status_driver", status);

      const res = await api.get(`/api/admin/drivers?${params.toString()}`);
      return res.data;
    } catch (error) {
      return handleError(error);
    }
  },

  getById: async (id) => {
    try {
      const res = await api.get(`/api/admin/drivers/${id}`);
      console.log("Data get driver by id dari service", res);
      return res.data;
    } catch (error) {
      handleError(error);
    }
  },

  create: async (data) => {
    try {
      const res = await api.post("/api/admin/drivers", data);
      return res.data;
    } catch (error) {
      handleError(error);
    }
  },

  update: async (id, data) => {
    try {
      const res = await api.put(`/api/admin/drivers/${id}`, data);
      return res.data;
    } catch (error) {
      handleError(error);
    }
  },

  verify: async (id, data) => {
    try {
      const res = await api.patch(`/api/admin/drivers/${id}/verify`, data);
      return res.data;
    } catch (error) {
      handleError(error);
      return null;
    }
  },

  remove: async (id) => {
    try {
      const res = await api.delete(`/api/admin/drivers/${id}`);
      return res.data;
    } catch (error) {
      handleError(error);
    }
  },
};

export default driverService;
