import userService from "../service/userService";
import { useState, useEffect, useCallback } from "react";
import useAuth from "./useAuth";
import axios from "axios";

export function useUsers({ search = "", status = "", role = "" } = {}) {
  const { user, loading: authLoading } = useAuth();
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState();
  const [error, setError] = useState(null);
  const [meta, setMeta] = useState({});
  const [links, setLinks] = useState({});

  const [isLoadingList, setIsLoadingList] = useState(false);
  const [isLoadingDetail, setIsLoadingDetail] = useState(false);
  const [isLoadingAction, setIsLoadingAction] = useState(false);

  const handleError = useCallback((err) => {
    console.log("Driver hook error :", err);

    setError({
      message: err?.response?.data?.message || err.message,
      code: err?.response?.status || null,
      fields: err?.response?.data?.errors || null,
    });
  }, []);

  const fetchUsers = useCallback(
    async (page = 1) => {
      if (authLoading || !user) return;
      setIsLoadingList(true);
      setError(null);

      try {
        const response = await userService.getAll({
          page,
          search,
          status,
          role,
        });

        if (response.success) {
          setUsers(response.data || []);
          setMeta(response.meta || {});
          setLinks(response.links || {});
        } else {
          setUsers([]);
          setMeta({
            current_page: 1,
            last_page: 1,
            per_page: 10,
            total: 0,
          });
          setLinks({
            next_page_url: null,
            prev_page_url: null,
          });
        }
      } catch (err) {
        if (!axios.isCancel(err)) {
          handleError(err);
        }
      } finally {
        setIsLoadingList(false);
      }
    },
    [authLoading, user, handleError, search, status, role]
  );

  const getUserById = useCallback(
    async (id) => {
      setIsLoadingDetail(true);
      setError(null);
      try {
        const user = await userService.getById(id);
        console.log("User dari hook: ", user);
        setSelectedUser(user.data);
        return user;
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingDetail(false);
      }
    },
    [handleError]
  );

  const createUser = useCallback(
    async (payload) => {
      setIsLoadingAction(true);
      try {
        await userService.create(payload);
        await fetchUsers(); // refresh data
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchUsers, handleError]
  );

  const updateUser = useCallback(
    async (id, payload) => {
      setIsLoadingAction(true);
      try {
        await userService.update(id, payload);
        await fetchUsers();
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchUsers, handleError]
  );

  const verifyUser = useCallback(
    async (id, payload) => {
      setIsLoadingAction(true);
      try {
        const result = await userService.verify(id, payload);
        console.log("✨ verifyUser result:", result); // <-- log hasil verify
        await fetchUsers();
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchUsers, handleError]
  );

  const deleteUser = useCallback(
    async (id) => {
      setIsLoadingAction(true);
      try {
        await userService.remove(id);
        setUsers((prev) => prev.filter((d) => d.id !== id));
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [handleError]
  );

  useEffect(() => {
    if (!authLoading && user) {
      fetchUsers();
    }
  }, [fetchUsers, authLoading, user]);

  return {
    users,
    isLoadingAction,
    isLoadingDetail,
    isLoadingList,
    error,
    meta,
    links,
    selectedUser,
    fetchUsers,
    createUser,
    updateUser,
    deleteUser,
    verifyUser,
    getUserById,
  };
}
