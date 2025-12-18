import {
  Home,
  Users,
  User,
  PieChart,
  Presentation,
  NotebookText,
  BanknoteArrowDown,
  BadgeCheck,
  UserStar,
  HandCoins,
  Columns3Cog,
  ChartPie,
  WalletMinimal,
  Building2,
} from "lucide-react";

export const sideBarMenu = {
  admin: [
    { name: "Beranda", icon: Home, path: "/home" },
    {
      name: "Verifikasi Data",
      icon: ChartPie,
      children: [
        { name: "Semua User", path: "/verifikasi" },
        { name: "Mitra", path: "/verifikasiMitra" },
        { name: "Customer", path: "/verifikasiCustomer" },
      ],
    },
    {
      name: "Mitra",
      icon: User,
      children: [
        { name: "Daftar Mitra", path: "/mitra" },
        { name: "Daftar Kendaraan", path: "/kendaraan" },
      ],
    },
    { name: "Customer", icon: Users, path: "/customer" },
    { name: "Pesanan", icon: NotebookText, path: "/pesanan" },
    { name: "Refund", icon: BanknoteArrowDown, path: "/refund" },
    { name: "Laporan", icon: Presentation, path: "/laporan" },
  ],

  superadmin: [
    { name: "Dashboard", icon: PieChart, path: "/sa/dashboard" },
    { name: "Layanan", icon: Columns3Cog, path: "/sa/layanan" },
    {
      name: "Verifikasi Data",
      icon: ChartPie,
      children: [
        { name: "Semua User", path: "/verifikasi" },
        { name: "Mitra", path: "/verifikasiMitra" },
        { name: "Customer", path: "/verifikasiCustomer" },
      ],
    },
    {
      name: "Mitra",
      icon: User,
      children: [
        { name: "Daftar Mitra", path: "/mitra" },
        { name: "Daftar Kendaraan", path: "/kendaraan" },
      ],
    },
    { name: "Customer", icon: Users, path: "/customer" },
    { name: "Admin", icon: UserStar, path: "/admin" },
    { name: "Pesanan", icon: NotebookText, path: "/pesanan" },
    { name: "Refund", icon: BanknoteArrowDown, path: "/refund" },
    { name: "Pencairan Dana", icon: HandCoins, path: "/pencairan" },
    { name: "Laporan", icon: Presentation, path: "/laporan" },
  ],
  finance: [
    { name: "Dashboard", icon: Home, path: "/dashboardFinance" },
    { name: "Transaksi", icon: NotebookText, path: "/transaksi" },
    { name: "Mitra", icon: User, path: "/mitra" },
    { name: "Pos Mitra", icon: Building2, path: "/posMitra" },
    { name: "Pencairan Dana", icon: WalletMinimal, path: "/pencairanDana" },
  ],
};
