import { useEffect, useState } from "react";

type ChangeEvent = React.ChangeEvent<HTMLInputElement>;
type Theme = 'dark' | 'light';

// initial parameters
type useThemeReturn = [string, (e:ChangeEvent) => void];

export const useTheme = (useThemeReturn:Theme): useThemeReturn => {
    const [theme, setTheme] = useState<Theme>('light');
    const handleChangeTheme = (e: ChangeEvent) => setTheme(e.target.checked ? 'dark' : 'light');

    useEffect(() => {
        document.body.setAttribute('data-bs-theme', theme);
    }, [theme]);
    return [theme, handleChangeTheme];
}