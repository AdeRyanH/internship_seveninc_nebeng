import React from "react";
import Header from "./Header";
import SideBar from "./SideBar";

export default function layout({ children }) {
  return (
    <div className="flex min-h-screen min-w-full no-scrollbar overflow-auto">
      {/* Sidebar */}
      <SideBar />

      {/* Konten Utama */}
      <div className="flex-1 flex flex-col bg-gray-100 min-w-full  no-scrollbar overflow-y-auto">
        {/* Header */}
        <Header />

        {/* Isi halaman */}
        <main className="pt-20 pl-68">{children}</main>
      </div>
    </div>
  );
}
