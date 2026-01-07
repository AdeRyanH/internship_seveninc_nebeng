"use client";

import { Dialog, DialogPanel } from "@headlessui/react";
import { Bars3Icon, XMarkIcon } from "@heroicons/react/24/outline";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../api/auth";
import { useState } from "react";
import useAuth from "../hooks/useAuth";
import bg from "../assets/bg.jpg";

export default function Login() {
  const { login } = useAuth();
  const [userIdentifier, setUserIdentifier] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    console.log({ userIdentifier, password });

    try {
      const response = await loginUser({ userIdentifier, password });
      const { user, token } = response.data;
      login(user, token);

      if (user.user_type === "admin") {
        navigate("/home");
      } else if (user.user_type === "superadmin") {
        navigate("/sa/dashboard");
      } else if (user.user_type === "finance") {
        navigate("dashboardFinance");
      } else {
        navigate("/");
      }
    } catch (error) {
      console.error("Login error :", error.message);
      alert("Login gagal, periksa kembali email / username dan password");
    } finally {
      setLoading(false);
    }
  };
  return (
    <div className="flex w-full min-h-screen overflow-hidden">
      <div
        className="relative w-1/2 min-h-screen bg-cover bg-center bg-no-repeat flex"
        style={{ backgroundImage: `url(${bg})` }}
      >
        <div className="absolute inset-0 bg-blue-900/97 z-8">
          <img
            className="w-37 mt-4 ml-4"
            src="http://localhost:5173/logoNebeng.png"
            alt="logo"
          />
        </div>
        <div className="absolute right-0 top-10 z-10">
          <svg
            width="161"
            height="174"
            viewBox="0 0 161 174"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <g filter="url(#filter0_d_704_16696)">
              <path
                d="M120.011 43.4118C126.276 73.1546 150.03 130.862 121.221 137.33C92.4111 143.799 12.1512 112.315 5.88574 82.5721C-0.379743 52.8293 27.6925 7.73832 56.502 1.26985C85.3115 -5.19861 113.745 13.669 120.011 43.4118Z"
                fill="url(#paint0_linear_704_16696)"
              />
            </g>
            <defs>
              <filter
                id="filter0_d_704_16696"
                x="0"
                y="0"
                width="160.166"
                height="173.184"
                filterUnits="userSpaceOnUse"
                colorInterpolationFilters="sRGB"
              >
                <feFlood floodOpacity="0" result="BackgroundImageFix" />
                <feColorMatrix
                  in="SourceAlpha"
                  type="matrix"
                  values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
                  result="hardAlpha"
                />
                <feOffset dx="10" dy="20" />
                <feGaussianBlur stdDeviation="7.5" />
                <feComposite in2="hardAlpha" operator="out" />
                <feColorMatrix
                  type="matrix"
                  values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.05 0"
                />
                <feBlend
                  mode="normal"
                  in2="BackgroundImageFix"
                  result="effect1_dropShadow_704_16696"
                />
                <feBlend
                  mode="normal"
                  in="SourceGraphic"
                  in2="effect1_dropShadow_704_16696"
                  result="shape"
                />
              </filter>
              <linearGradient
                id="paint0_linear_704_16696"
                x1="1.15095"
                y1="60.0956"
                x2="326.012"
                y2="493.353"
                gradientUnits="userSpaceOnUse"
              >
                <stop stopColor="#002872" />
                <stop offset="1" stopColor="#947AEC" />
              </linearGradient>
            </defs>
          </svg>
        </div>
        <div className="absolute left-0 bottom-20 z-10">
          <svg
            width="166"
            height="267"
            viewBox="0 0 166 267"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <g opacity="0.77" filter="url(#filter0_d_704_16710)">
              <path
                d="M1.97452 200.066C43.778 212.931 122.812 258.251 137.298 208.081C151.784 157.91 120.696 15.0355 78.8929 2.17079C56.3285 -4.77315 25.5228 5.56622 -0.331962 24.4435L-0.897402 199.111C0.0491342 199.45 1.0067 199.768 1.97452 200.066Z"
                fill="url(#paint0_linear_704_16710)"
              />
            </g>
            <defs>
              <filter
                id="filter0_d_704_16710"
                x="-5.89648"
                y="0"
                width="171.803"
                height="266.168"
                filterUnits="userSpaceOnUse"
                colorInterpolationFilters="sRGB"
              >
                <feFlood floodOpacity="0" result="BackgroundImageFix" />
                <feColorMatrix
                  in="SourceAlpha"
                  type="matrix"
                  values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
                  result="hardAlpha"
                />
                <feOffset dx="10" dy="20" />
                <feGaussianBlur stdDeviation="7.5" />
                <feComposite in2="hardAlpha" operator="out" />
                <feColorMatrix
                  type="matrix"
                  values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.05 0"
                />
                <feBlend
                  mode="normal"
                  in2="BackgroundImageFix"
                  result="effect1_dropShadow_704_16710"
                />
                <feBlend
                  mode="normal"
                  in="SourceGraphic"
                  in2="effect1_dropShadow_704_16710"
                  result="shape"
                />
              </filter>
              <linearGradient
                id="paint0_linear_704_16710"
                x1="-0.76958"
                y1="159.625"
                x2="419.536"
                y2="-522.328"
                gradientUnits="userSpaceOnUse"
              >
                <stop stopColor="#002872" />
                <stop offset="1" stopColor="#947AEC" />
              </linearGradient>
            </defs>
          </svg>
        </div>
        <div className="relative text-white z-12 flex items-center w-full">
          <div className="flex flex-col m-4 text-6xl font-semibold ">
            <h2 className="">Halo,</h2>
            <h2>Selamat Datang</h2>
          </div>
        </div>
        <div className="absolute -right-8 -bottom-20">
          <svg
            width="334"
            height="155"
            viewBox="0 0 334 155"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <g opacity="0.48" filter="url(#filter0_d_704_16722)">
              <path
                d="M15 75C15 47.3858 37.3858 25 65 25H299V155H15V75Z"
                fill="url(#paint0_linear_704_16722)"
              />
            </g>
            <defs>
              <filter
                id="filter0_d_704_16722"
                x="0"
                y="0"
                width="334"
                height="180"
                filterUnits="userSpaceOnUse"
                colorInterpolationFilters="sRGB"
              >
                <feFlood floodOpacity="0" result="BackgroundImageFix" />
                <feColorMatrix
                  in="SourceAlpha"
                  type="matrix"
                  values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
                  result="hardAlpha"
                />
                <feOffset dx="10" />
                <feGaussianBlur stdDeviation="12.5" />
                <feComposite in2="hardAlpha" operator="out" />
                <feColorMatrix
                  type="matrix"
                  values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.1 0"
                />
                <feBlend
                  mode="normal"
                  in2="BackgroundImageFix"
                  result="effect1_dropShadow_704_16722"
                />
                <feBlend
                  mode="normal"
                  in="SourceGraphic"
                  in2="effect1_dropShadow_704_16722"
                  result="shape"
                />
              </filter>
              <linearGradient
                id="paint0_linear_704_16722"
                x1="15"
                y1="65.0537"
                x2="103.168"
                y2="578.367"
                gradientUnits="userSpaceOnUse"
              >
                <stop stopColor="#10367D" />
                <stop offset="1" stopColor="#947AEC" />
              </linearGradient>
            </defs>
          </svg>
        </div>
      </div>
      {/* Sisi Kanan */}
      <div className="w-1/2 flex items-center justify-center bg-white border-l-1 border-black">
        <div className=" w-full max-w-xl z-10">
          <div className="bg-white rounded-xl p-5 min-w-lg">
            <div className="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
              <div className="sm:mx-auto sm:w-full sm:max-w-sm">
                <h2 className="mt-3 text-xl/9 font-semibold tracking-tight text-black">
                  Login untuk melanjutkan ke Dashboard Nebeng
                </h2>
              </div>

              <div className="relative input-group mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <form
                  onSubmit={handleLogin}
                  method="POST"
                  className="space-y-6"
                >
                  <div>
                    <div className="relative mt-4">
                      <input
                        id="userIdentifier"
                        name="userIdentifier"
                        value={userIdentifier}
                        onChange={(e) => setUserIdentifier(e.target.value)}
                        placeholder=" "
                        type="text"
                        required
                        autoComplete="username"
                        className="peer w-full rounded-md border border-gray-300
      px-3 pt-5 pb-2 text-sm text-black
      focus:border-indigo-500 focus:outline-none"
                      />
                      <label
                        htmlFor="userIdentifier"
                        className="absolute left-3 top-3 text-gray-500 text-sm
      transition-all duration-200
      bg-white px-1

      peer-placeholder-shown:top-3
      peer-placeholder-shown:text-sm

      peer-focus:-top-2
      peer-focus:text-xs
      peer-focus:text-indigo-500

      peer-not-placeholder-shown:-top-2
      peer-not-placeholder-shown:text-xs"
                      >
                        Email / Username
                      </label>
                    </div>
                  </div>

                  <div className="relative mt-4">
                    <input
                      id="password"
                      name="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      type="password"
                      required
                      placeholder=" "
                      autoComplete="current-password"
                      className="
      peer w-full rounded-md border border-gray-300
      px-3 pt-5 pb-2 text-sm text-black
      focus:border-indigo-500 focus:outline-none
    "
                    />

                    <label
                      htmlFor="password"
                      className="
      absolute left-3 top-3 text-gray-500 text-sm
      transition-all duration-200
      bg-white px-1

      peer-placeholder-shown:top-3
      peer-placeholder-shown:text-sm

      peer-focus:-top-2
      peer-focus:text-xs
      peer-focus:text-indigo-500

      peer-not-placeholder-shown:-top-2
      peer-not-placeholder-shown:text-xs
    "
                    >
                      Password
                    </label>
                  </div>

                  <div>
                    <button
                      type="submit"
                      className={`flex w-full justify-center rounded-md px-3 py-2 text-sm font-semibold text-white ${
                        loading
                          ? "bg-gray-400 cursor-not-allowed"
                          : "bg-indigo-600 hover:bg-indigo-500"
                      } focus-visible:outline focus-visible:outline-offset-2 focus-visible:outline-indigo-600`}
                    >
                      {loading ? (
                        <div className="text-center flex">
                          <p className="mr-2">Loading</p>
                          <svg
                            className="w-6 h-6 mx-auto text-gray-500 animate-spin"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                          >
                            <circle
                              className="opacity-25"
                              cx="12"
                              cy="12"
                              r="10"
                              stroke="currentColor"
                              strokeWidth="4"
                            ></circle>
                            <path
                              className="opacity-75"
                              fill="currentColor"
                              d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"
                            ></path>
                          </svg>
                        </div>
                      ) : (
                        "Masuk"
                      )}
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    // <div className="bg-blue-500 overflow-hidden h-screen flex items-center justify-center">
    //   <header className="absolute inset-x-0 top-0 z-50">
    //     <nav
    //       aria-label="Global"
    //       className="flex items-center justify-between p-6 lg:px-8"
    //     >
    //       <div className="flex lg:flex-1">
    //         <a href="#" className="-m-1.5 p-1.5">
    //           <span className="sr-only">Your Company</span>
    //           <img
    //             alt=""
    //             src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=500"
    //             className="h-8 w-auto"
    //           />
    //         </a>
    //       </div>
    //     </nav>
    //   </header>

    //   <div className="relative isolate px-6 lg:px-8">
    //     <div className=" flex justify-between">
    //       <div
    //         aria-hidden="true"
    //         className="fixed flex-row inset-x-0 top-0 -z-10 transform-gpu left-0 overflow-hidden"
    //       >
    //         <svg
    //           width="655"
    //           height="720"
    //           viewBox="0 0 655 720"
    //           fill="none"
    //           xmlns="http://www.w3.org/2000/svg"
    //           className="w-auto h-auto max-w-[50vw] max-h-[50vh]"
    //         >
    //           <path
    //             opacity="0.6"
    //             fillRule="evenodd"
    //             clipRule="evenodd"
    //             d="M53.5123 -124.947C-178.73 -40.418 -298.476 216.376 -213.946 448.619C-168.757 572.775 93.3961 360.789 206.979 406.621C305.861 446.521 251.534 755.418 359.62 716.077C591.863 631.548 711.608 374.754 627.079 142.511C542.549 -89.7317 285.755 -209.477 53.5123 -124.947Z"
    //             fill="#0C1F3D"
    //             fillOpacity="0.1"
    //           />
    //         </svg>
    //       </div>
    //       <div className="fixed flex-row right-0 top-0 -z-10 transform-gpu overflow-hidden">
    //         <svg
    //           width="750"
    //           height="505"
    //           viewBox="0 0 750 505"
    //           fill="none"
    //           xmlns="http://www.w3.org/2000/svg"
    //           className="w-auto h-auto max-w-[50vw] max-h-[50vh]"
    //         >
    //           <path
    //             opacity="0.541829"
    //             fillRule="evenodd"
    //             clipRule="evenodd"
    //             d="M8.46914 -99.9152C-41.4964 183.454 147.714 453.675 431.083 503.64C582.571 530.352 521.439 142.63 633.769 54.7875C731.56 -21.6852 1011.38 212.907 1034.64 81.0262C1084.6 -202.343 895.394 -472.564 612.025 -522.529C328.656 -572.495 58.4347 -383.284 8.46914 -99.9152Z"
    //             fill="#0C1F3D"
    //             fillOpacity="0.1"
    //           />
    //         </svg>
    //       </div>
    //     </div>
    //     <div className=" w-full max-w-xl z-10">
    //       <div className="bg-white rounded-xl p-5 min-w-lg">
    //         <div className="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
    //           <div className="sm:mx-auto sm:w-full sm:max-w-sm">
    //             <h2 className="mt-3 text-center text-2xl/9 font-bold tracking-tight text-black">
    //               Masuk
    //             </h2>
    //             <p className="font-medium text-gray-600 text-center mt-2">
    //               Masukkan email dan password anda
    //             </p>
    //           </div>

    //           <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
    //             <form
    //               onSubmit={handleLogin}
    //               method="POST"
    //               className="space-y-6"
    //             >
    //               <div>
    //                 <label
    //                   htmlFor="userIdentifier"
    //                   className="block text-sm/6 font-medium text-black"
    //                 >
    //                   Email / Username
    //                 </label>
    //                 <div className="mt-2">
    //                   <input
    //                     id="userIdentifier"
    //                     name="userIdentifier"
    //                     value={userIdentifier}
    //                     onChange={(e) => setUserIdentifier(e.target.value)}
    //                     type="text"
    //                     required
    //                     autoComplete="username"
    //                     className="block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-black outline-1 -outline-offset-1 outline-gray-500 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-500 sm:text-sm/6"
    //                   />
    //                 </div>
    //               </div>

    //               <div>
    //                 <div className="flex items-center justify-between">
    //                   <label
    //                     htmlFor="password"
    //                     className="block text-sm/6 font-medium text-black"
    //                   >
    //                     Password
    //                   </label>
    //                 </div>
    //                 <div className="mt-2">
    //                   <input
    //                     id="password"
    //                     name="password"
    //                     value={password}
    //                     onChange={(e) => setPassword(e.target.value)}
    //                     type="password"
    //                     required
    //                     autoComplete="current-password"
    //                     className="block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-black outline-1 -outline-offset-1 outline-gray-500 placeholder:text-gray-500 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-500 sm:text-sm/6"
    //                   />
    //                 </div>
    //               </div>

    //               <div>
    //                 <button
    //                   type="submit"
    //                   className={`flex w-full justify-center rounded-md px-3 py-2 text-sm font-semibold text-white ${
    //                     loading
    //                       ? "bg-gray-400 cursor-not-allowed"
    //                       : "bg-indigo-600 hover:bg-indigo-500"
    //                   } focus-visible:outline focus-visible:outline-offset-2 focus-visible:outline-indigo-600`}
    //                 >
    //                   {loading ? (
    //                     <div className="text-center flex">
    //                       <p className="mr-2">Loading</p>
    //                       <svg
    //                         className="w-6 h-6 mx-auto text-gray-500 animate-spin"
    //                         xmlns="http://www.w3.org/2000/svg"
    //                         fill="none"
    //                         viewBox="0 0 24 24"
    //                       >
    //                         <circle
    //                           className="opacity-25"
    //                           cx="12"
    //                           cy="12"
    //                           r="10"
    //                           stroke="currentColor"
    //                           strokeWidth="4"
    //                         ></circle>
    //                         <path
    //                           className="opacity-75"
    //                           fill="currentColor"
    //                           d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"
    //                         ></path>
    //                       </svg>
    //                     </div>
    //                   ) : (
    //                     "Masuk"
    //                   )}
    //                 </button>
    //               </div>
    //             </form>
    //           </div>
    //         </div>
    //       </div>
    //     </div>
    //     <div className="flex justify-between w-full min-h-full">
    //       <div aria-hidden="true" className="fixed bottom-0 left-0 -z-10">
    //         <svg
    //           width="554"
    //           height="430"
    //           viewBox="0 0 554 430"
    //           fill="none"
    //           xmlns="http://www.w3.org/2000/svg"
    //           className="w-auto h-auto max-w-[40vw] max-h-[40vh]"
    //         >
    //           <path
    //             fillRule="evenodd"
    //             clipRule="evenodd"
    //             d="M106.5 895C353.647 895 554 694.647 554 447.5C554 315.376 235.153 424.916 144.095 343C64.8233 271.687 221.523 0 106.5 0C-140.647 0 -341 200.353 -341 447.5C-341 694.647 -140.647 895 106.5 895Z"
    //             fill="#0C1F3D"
    //             fillOpacity="0.1"
    //           />
    //         </svg>
    //       </div>
    //       <div aria-hidden="true" className="fixed bottom-0 right-0 -z-10">
    //         <svg
    //           width="741"
    //           height="551"
    //           viewBox="0 0 741 551"
    //           fill="none"
    //           xmlns="http://www.w3.org/2000/svg"
    //           className="w-auto h-auto max-w-[50vw] max-h-[50vh]"
    //         >
    //           <path
    //             fillRule="evenodd"
    //             clipRule="evenodd"
    //             d="M1141.38 667.958C1196.52 355.219 987.699 56.9905 674.96 1.84616C507.77 -27.6339 575.24 400.274 451.267 497.221C343.34 581.62 34.5121 322.713 8.8477 468.263C-46.2967 781.002 162.525 1079.23 475.264 1134.38C788.004 1189.52 1086.23 980.698 1141.38 667.958Z"
    //             fill="#0C1F3D"
    //             fillOpacity="0.1"
    //           />
    //         </svg>
    //       </div>
    //     </div>
    //   </div>
    // </div>
  );
}
