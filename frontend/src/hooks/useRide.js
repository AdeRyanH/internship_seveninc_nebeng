import { useState, useEffect, useCallback } from "react";
import useAuth from "./useAuth";
import rideService from "../service/rideService";

export function useRides() {
  const { user, loading: authLoading } = useAuth();
  const [rides, setRides] = useState([]);
  const [error, setError] = useState(null);
  const [meta, setMeta] = useState({});
  const [links, setLinks] = useState({
    next_page_url: null,
    prev_page_url: null,
  });
  const [isLoadingList, setIsLoadingList] = useState(false);
  const [isLoadingDetail, setIsLoadingDetail] = useState(false);

  const fetchRides = useCallback(
    async (page = 1) => {
      if (authLoading || !user) return;
      setIsLoadingList(true);
      setError(null);
      try {
        const response = await rideService.getAll({ page });
        console.log("📦 response ride:", response);
        if (response.success) {
          setRides(response.data || []);
          setMeta(response.meta || {});
          setLinks(response.links || {});
        } else {
          setRides([]);
          setMeta({
            current_page: 1,
            last_page: 1,
            per_page: 10,
            total: 0,
            next_page_url: null,
            prev_page_url: null,
          });
        }
      } catch (error) {
        setError(error);
      } finally {
        setIsLoadingList(false);
      }
    },
    [authLoading, user]
  );

  const fetchRideById = useCallback(async (type, id) => {
    if (!type || !id) return;
    setIsLoadingDetail(true);
    setError(null);
    try {
      const data = await rideService.getByBooking(type, id);
      console.log("📦 Data ride by booking:", data);
      setRides(data || null);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoadingDetail(false);
    }
  }, []);

  useEffect(() => {
    if (!authLoading && user) {
      fetchRides();
    }
  }, [fetchRides, authLoading, user]);

  return {
    rides,
    isLoadingList,
    isLoadingDetail,
    error,
    meta,
    links,
    fetchRides,
    fetchRideById,
  };
}
