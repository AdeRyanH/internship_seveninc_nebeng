import orderService from "../service/orderService";
import { useState, useEffect, useCallback, useMemo } from "react";
import useAuth from "./useAuth";
import { debounce } from "lodash";

export function useOrders({ search = "", status = "" } = {}) {
  const { user, loading: authLoading } = useAuth();
  const [orders, setOrders] = useState([]);
  const [selectedOrder, setSelectedOrder] = useState([]);
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

  const handleError = (err) => {
    console.error("Order hook error : ", err);
  };

  const fetchOrders = useCallback(
    async (page = 1) => {
      if (authLoading || !user) return;
      setIsLoadingList(true);
      setError(null);
      try {
        const response = await orderService.getAll({ page, search, status });
        // console.log("📦 Data orders dari service:", response);
        if (response.success) {
          setOrders(response.data || []);
          setMeta(response.meta || {});
          setLinks(response.links || {});
        } else {
          setOrders([]);
          setMeta({
            current_page: 1,
            last_page: 1,
            per_page: 10,
            total: 0,
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

  // Debounce pencarian
  const debouncedFetchOrders = useMemo(
    () =>
      debounce(() => {
        fetchOrders(1);
      }, 400),
    [fetchOrders]
  );

  useEffect(() => {
    if (!authLoading && user) {
      debouncedFetchOrders();
    }

    return () => {
      debouncedFetchOrders.cancel();
    };
  }, [search, status, authLoading, user, debouncedFetchOrders]);

  const getOrderById = useCallback(async (type, id) => {
    setIsLoadingDetail(true);
    setError(null);
    try {
      const order = await orderService.getOrderById(type, id);
      setSelectedOrder(order);
      console.log("Data order dari service", order);
      return order;
    } catch (err) {
      handleError(err);
    } finally {
      setIsLoadingDetail(false);
    }
  }, []);

  return {
    orders,
    error,
    meta,
    links,
    isLoadingList,
    isLoadingDetail,
    selectedOrder,
    fetchOrders,
    getOrderById,
  };
}
