import driverService from "../service/driverService";
import { useState, useEffect, useCallback } from "react";
import useAuth from "./useAuth";
import { Search } from "lucide-react";

export function useDrivers({ search = "", status = "" } = {}) {
  const { user, loading: authLoading } = useAuth();
  const [SelectedDriver, setSelectedDriver] = useState();
  const [drivers, setDrivers] = useState([]);
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

  const handleError = (err) => {
    console.error("Driver Hook Error : ", err);
  };

  const fetchDrivers = useCallback(
    async (page = 1) => {
      if (authLoading || !user) return;

      setIsLoadingList(true);
      setError({ message: null, code: null, fields: null });
      try {
        const response = await driverService.getAll({
          page,
          search,
          status,
        });
        // console.log("Data response drivers: ", response);
        if (response?.success) {
          setDrivers(response.data || []);
          setMeta(response.meta || {});
          setLinks(response.links || {});
        } else {
          setDrivers([]);
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
        handleError(err);
      } finally {
        setIsLoadingList(false);
      }
    },
    [authLoading, user, search, status]
  );

  const getDriverById = useCallback(async (id) => {
    setIsLoadingDetail(true);
    setError({ message: null, code: null, fields: null });
    try {
      const driver = await driverService.getById(id);
      setSelectedDriver(driver.data);
      return driver;
    } catch (err) {
      handleError(err);
      throw err;
    } finally {
      setIsLoadingDetail(false);
    }
  }, []);

  const createDriver = async (payload) => {
    setIsLoadingAction(true);
    try {
      await driverService.create(payload);
      await fetchDrivers(); // refresh data
    } catch (err) {
      handleError(err);
    } finally {
      setIsLoadingAction(false);
    }
  };

  const updateDriver = async (id, payload) => {
    setIsLoadingAction(true);
    try {
      await driverService.update(id, payload);
      await fetchDrivers();
    } catch (err) {
      handleError(err);
    } finally {
      setIsLoadingAction(false);
    }
  };

  const verifyDriver = async (id, payload) => {
    setIsLoadingAction(true);
    try {
      const result = await driverService.verify(id, payload);
      await fetchDrivers();

      const updateDriver = await getDriverById(id);
      return { ...result, data: updateDriver.data };
    } catch (err) {
      handleError(err);
    } finally {
      setIsLoadingAction(false);
    }
  };

  const deleteDriver = async (id) => {
    setIsLoadingAction(true);
    try {
      await driverService.remove(id);
      setDrivers((prev) => prev.filter((d) => d.id !== id));
    } catch (err) {
      handleError(err);
    } finally {
      setIsLoadingAction(false);
    }
  };

  useEffect(() => {
    if (!authLoading && user) {
      fetchDrivers();
    }
  }, [fetchDrivers, authLoading, user]);

  return {
    drivers,
    error,
    meta,
    links,
    isLoadingAction,
    isLoadingDetail,
    isLoadingList,
    SelectedDriver,
    fetchDrivers,
    verifyDriver,
    createDriver,
    updateDriver,
    deleteDriver,
    getDriverById,
  };
}
