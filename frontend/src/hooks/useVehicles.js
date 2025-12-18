import vehicleService from "../service/vehicleService";
import { useState, useEffect, useCallback } from "react";
import useAuth from "./useAuth";
import axios from "axios";

export function useVehicles({ search = "", status = "" } = {}) {
  const { user, loading: authLoading } = useAuth();
  const [vehicles, setVehicles] = useState([]);
  const [selectedVehicle, setSelectedVehicle] = useState();
  const [selectedDriver, setSelectedDriver] = useState();
  const [error, setError] = useState(null);
  const [meta, setMeta] = useState({
    current_page: 1,
    last_page: 1,
    per_page: 10,
    total: 0,
  });
  const [links, setLinks] = useState({
    next_page_url: null,
    prev_page_url: null,
  });

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

  const fetchVehicles = useCallback(
    async (page = 1) => {
      if (authLoading || !user) return;
      setIsLoadingList(true);
      setError(null);

      try {
        const response = await vehicleService.getAll({ page, search, status });

        if (response.success) {
          setVehicles(response.data || []);
          setMeta(response.meta || {});
          setLinks(response.links || {});
        } else {
          setVehicles([]);
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
    [authLoading, user, handleError, search, status]
  );

  const getVehicleById = useCallback(
    async (id) => {
      setIsLoadingDetail(true);
      setError(null);
      try {
        const vehicle = await vehicleService.getById(id);
        setSelectedVehicle(vehicle.data);
        return vehicle;
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingDetail(false);
      }
    },
    [handleError]
  );

  const getDriver = useCallback(
    async (driverId) => {
      setIsLoadingDetail(true);
      setError(null);
      try {
        const driver = await vehicleService.getDriver(driverId);
        setSelectedDriver(driver);
        return driver;
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingDetail(false);
      }
    },
    [handleError]
  );

  const createVehicle = useCallback(
    async (payload) => {
      setIsLoadingAction(true);
      try {
        await vehicleService.create(payload);
        await fetchVehicles(); // refresh data
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchVehicles, handleError]
  );

  const updateVehicle = useCallback(
    async (id, payload) => {
      setIsLoadingAction(true);
      try {
        await vehicleService.update(id, payload);
        await fetchVehicles();
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchVehicles, handleError]
  );

  const verifyVehicle = useCallback(
    async (id) => {
      setIsLoadingAction(true);
      try {
        const result = await vehicleService.verify(id);
        console.log("✨ verifyVehicle result:", result); // <-- log hasil verify
        await fetchVehicles();
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchVehicles, handleError]
  );

  const rejectVehicle = useCallback(
    async (id, reason) => {
      setIsLoadingAction(true);
      try {
        const result = await vehicleService.reject(id, reason);
        console.log("✨ rejectVehicleVehicle result:", result); // <-- log hasil verify
        await fetchVehicles();
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchVehicles, handleError]
  );

  const deleteVehicle = useCallback(
    async (id) => {
      setIsLoadingAction(true);
      try {
        await vehicleService.remove(id);
        setVehicles((prev) => prev.filter((d) => d.id !== id));
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
      fetchVehicles();
    }
  }, [fetchVehicles, authLoading, user]);

  return {
    vehicles,
    isLoadingAction,
    isLoadingDetail,
    isLoadingList,
    error,
    meta,
    links,
    selectedVehicle,
    selectedDriver,
    rejectVehicle,
    fetchVehicles,
    createVehicle,
    updateVehicle,
    deleteVehicle,
    verifyVehicle,
    getVehicleById,
    getDriver,
  };
}
