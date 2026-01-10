import customerService from "../service/customerService";
import { useState, useEffect, useCallback, useMemo } from "react";
import useAuth from "./useAuth";
import axios from "axios";
import { debounce } from "lodash";

export function useCustomers({ search = "", status = "" } = {}) {
  const { user, loading: authLoading } = useAuth();
  const [customers, setCustomers] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState();
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

  const fetchCustomers = useCallback(
    async (page = 1) => {
      if (authLoading || !user) return;
      setIsLoadingList(true);
      setError(null);

      try {
        const response = await customerService.getAll({ page, search, status });

        if (response.success) {
          setCustomers(response.data || []);
          setMeta(response.meta || {});
          setLinks(response.links || {});
        } else {
          setCustomers([]);
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

  // Debounce pencarian
  const debouncedFetchCustomers = useMemo(
    () =>
      debounce(() => {
        fetchCustomers(1);
      }, 400),
    [fetchCustomers]
  );

  useEffect(() => {
    if (!authLoading && user) {
      debouncedFetchCustomers();
    }

    return () => {
      debouncedFetchCustomers.cancel();
    };
  }, [search, status, authLoading, user, debouncedFetchCustomers]);

  const getCustomerById = useCallback(
    async (id) => {
      setIsLoadingDetail(true);
      setError(null);
      try {
        const customer = await customerService.getById(id);
        setSelectedCustomer(customer.data);
        return customer;
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingDetail(false);
      }
    },
    [handleError]
  );

  const createCustomer = useCallback(
    async (payload) => {
      setIsLoadingAction(true);
      try {
        await customerService.create(payload);
        await fetchCustomers(); // refresh data
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchCustomers, handleError]
  );

  const updateCustomer = useCallback(
    async (id, payload) => {
      setIsLoadingAction(true);
      try {
        await customerService.update(id, payload);
        await fetchCustomers();
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchCustomers, handleError]
  );

  const verifyCustomer = useCallback(
    async (id, payload) => {
      setIsLoadingAction(true);
      try {
        await customerService.verify(id, payload);
        await fetchCustomers();
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [fetchCustomers, handleError]
  );

  const deleteCustomer = useCallback(
    async (id) => {
      setIsLoadingAction(true);
      try {
        await customerService.remove(id);
        setCustomers((prev) => prev.filter((d) => d.id !== id));
      } catch (err) {
        handleError(err);
      } finally {
        setIsLoadingAction(false);
      }
    },
    [handleError]
  );

  return {
    customers,
    isLoadingAction,
    isLoadingDetail,
    isLoadingList,
    error,
    meta,
    links,
    selectedCustomer,
    fetchCustomers,
    createCustomer,
    updateCustomer,
    deleteCustomer,
    verifyCustomer,
    getCustomerById,
  };
}
