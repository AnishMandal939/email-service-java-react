import { useTheme } from "../../hooks/useTheme";

export const LightSwitch = () => {
  const [theme, handleChangeTheme] = useTheme("dark");

  return (
    <div className="container-switch flex gap-2 p-2 align-middle float-end">
      <span>Change Theme</span>
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
  );
};
