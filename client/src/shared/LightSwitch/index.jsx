import { useTheme } from "../../hooks/useTheme";

export const LightSwitch = () => {
  const [theme, handleChangeTheme] = useTheme("dark");

  return (
    <div className="container-switch flex p-2 justify-between align-center float-end sticky w-full z-20 top-0 start-0 dark:border-gray-600 backdrop-blur-sm border-b-white-500 border border-collapse border-r-0 border-l-0 border-t-0">
      <a
        href="https://anishmandal.yzzz.me"
        className="flex items-center space-x-3 rtl:space-x-reverse"
      >
        <img
          src="https://res.cloudinary.com/dteke1ota/image/upload/v1713593711/mail-sender-logo_ay4eub.png"
          className="h-12"
          alt="mail sender logo"
        />
        <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">
          Mail Sender
        </span>

      </a>
      <div className="px-2">
        <a href="https://anishmandal.yzzz.me" target="_blank" className="fw-bold dark:text-green-500 dark:hover:text-green-600 hover:text-purple-700 text-purple-600 cursor-pointer">Navigate to Portfolio</a>
        <span className="px-2">Change Theme</span>
        <label className="switch">
          <input
            type="checkbox"
            onChange={handleChangeTheme}
            checked={theme === "dark"}
          />
          <span className="slider round">
            <span className={theme === "dark" ? "☀️" : "🌛"}></span>
          </span>
        </label>
      </div>
    </div>
  );
};
