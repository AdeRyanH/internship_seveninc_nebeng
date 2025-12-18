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

const userService = {
  getAll: async ({ page = 1, search = "", status = "", role = "" }) => {
    try {
      const params = new URLSearchParams();

      params.append("page", page);

      if (search) params.append("search", search);
      if (status) params.append("status", status);
      if (Array.isArray(role)) {
        role.forEach((r) => params.append("role[]", r));
      }

      const res = await api.get(`/api/users?${params.toString()}`);
      // console.log("Data dari user service :", res);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  getById: async (id) => {
    try {
      const res = await api.get(`/api/users/${id}`);
      console.log("Data detail user dari service : ", res);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  getByUserId: async (user_id) => {
    try {
      const res = await api.get(`/api/users/${user_id}`);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  create: async (data) => {
    try {
      const res = await api.post("/api/users", data);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  update: async (id, data) => {
    try {
      const res = await api.put(`/api/users/${id}`, data);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  verify: async (id, status) => {
    try {
      const res = await api.patch(`/api/users/${id}/verify`, {
        status,
      });
      console.log("📥 FE RECEIVE RESPONSE:", res.data); // <-- log respon
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },

  delete: async (id) => {
    try {
      const res = await api.delete(`/api/users/${id}`);
      return res.data;
    } catch (error) {
      handleError(error);
      throw error;
    }
  },
};

export default userService;
