import { useNavigate } from "react-router-dom";
import { useState } from "react";
import useAuth from "../hooks/useAuth";
import LoadingPortal from "./LoadingPortal";

export default function LogoutButton() {
  const { logout } = useAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const handleLogout = async () => {
    setLoading(true);
    try {
      await logout();
      navigate("/login");
    } catch (error) {
      alert("Logout gagal, coba lagi");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {/* Global Loading */}
      <LoadingPortal isOpen={loading} />

      <li>
        <button
          onClick={handleLogout}
          className="relative flex items-center w-full text-left px-4 py-3 transition-all duration-300 group text-white dark:text-gray-300 hover:text-red-500 hover:before:content-[''] hover:before:absolute hover:before:left-0 hover:before:top-0 hover:before:h-full hover:before:w-[3px] hover:before:bg-red-500 hover:before:rounded-r-md"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="size-6 w-4 mr-3"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15M12 9l-3 3m0 0 3 3m-3-3h12.75"
            />
          </svg>
          Keluar
        </button>
      </li>
    </>
  );
}
