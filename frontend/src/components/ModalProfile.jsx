import Input from "./Input";

export default function ProfileModal({ user, onClose }) {
  return (
    <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-9999">
      <div className="bg-white dark:bg-green-800 rounded-2xl shadow-xl w-96 overflow-hidden animate-scaleIn border border-gray-200">
        {/* HEADER */}
        <div className="bg-linear-to-r from-blue-800 to-blue-400 p-4 flex justify-between items-center">
          <h2 className="text-lg font-semibold text-white">Profile</h2>
          <button
            onClick={onClose}
            className="text-white/80 hover:text-white transition text-lg"
          >
            ✕
          </button>
        </div>

        {/* BODY */}
        <div className="p-6 pt-4 flex flex-col items-center text-start space-y-4">
          <img
            src={user?.image || "https://placehold.co/200"}
            className="w-24 h-24 rounded-full object-cover shadow-md ring-2 ring-green-500/40"
          />

          <div className="w-full space-y-2">
            <Input label="Nama" value={user?.name} className="w-xs" />
            <Input label="Username" value={user?.username} className="w-xs" />
            <Input label="Email" value={user?.email} className="w-xs" />
          </div>
        </div>

        {/* FOOTER */}
        <div className="bg-linear-to-r from-blue-400 to-blue-800 p-4 flex justify-end">
          <button
            onClick={onClose}
            className="px-4 py-2 bg-white/20 text-white border border-white/40 rounded-xl hover:bg-white/30 transition shadow"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
}
