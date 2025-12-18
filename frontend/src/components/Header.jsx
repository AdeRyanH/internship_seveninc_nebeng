import useAuth from "../hooks/useAuth";
import { useLocation } from "react-router-dom";
import ProfileModal from "./ModalProfile";
import { useState } from "react";

export default function Header() {
  const { user } = useAuth();
  const location = useLocation();
  const [openProfile, setOpenProfile] = useState(false);

  const getPageTitle = () => {
    const path = location.pathname;

    if (path === "/mitra") return "Data Mitra";
    if (path.startsWith("/mitra/")) return "Detail Mitra";
    if (path === "/customer") return "Data Customer";
    if (path.startsWith("/customer/")) return "Detail Customer";
    if (path.startsWith("/orders")) return "Detail Pesanan";
    if (path === "/pesanan") return "Data Pesanan";
    if (path.startsWith("/refund")) return "Data Refund";
    if (path.startsWith("/laporan")) return "Data Laporan";
    if (path === "/verifikasi") return "Data User";
    if (path.startsWith("/verifikasi")) return "Verifikasi User";
    if (path === "/pengaturan") return "Pengaturan";
  };
  return (
    <>
      <header className="fixed top-0 left-64 bg-gray-100 w-[calc(100%-16rem)] dark:bg-gray-400 dark:text-white py-3 h-17 text-end border-b border-gray-200 flex items-center justify-between z-50 max-w-full">
        <div className="pl-9">
          <h1 className="text-2xl font-bold">{getPageTitle()}</h1>
        </div>
        <div className="flex items-center">
          <div
            className="flex items-center space-x-3 cursor-pointer group"
            onClick={() => setOpenProfile(true)}
          >
            <img
              src={user?.image || "https://placehold.co/600x400"}
              alt="User Avatar"
              className="w-10 h-10 rounded-full object-cover border border-gray-300"
            />
          </div>
          <div className="flex flex-col p-3">
            <span className="font-semibold flex capitalize">{user?.name}</span>
            <span className="text-xs flex capitalize text-gray-500">
              {user?.user_type}
            </span>
          </div>
        </div>
      </header>

      {/* Profile Modal */}
      {openProfile && (
        <ProfileModal user={user} onClose={() => setOpenProfile(false)} />
      )}
    </>
  );
}
