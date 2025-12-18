import React from "react";
import ReactDOM from "react-dom";

export default function LoadingPortal({ isOpen }) {
  if (!isOpen) return null;

  return ReactDOM.createPortal(
    <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-black/50 backdrop-blur-sm animate-fadeIn">
      <div className="flex flex-col items-center p-6 rounded-2xl bg-white/20 backdrop-blur-md border border-white/30 shadow-xl">
        {/* Spinner Modern */}
        <div className="w-12 h-12 border-4 border-white/40 border-t-white/90 rounded-full animate-spin"></div>

        {/* Text */}
        <p className="mt-4 text-white text-sm tracking-wide animate-pulse">
          Loading...
        </p>
      </div>
    </div>,
    document.body
  );
}
