import React, {createContext, useContext, useEffect, useState} from 'react';
import {useColorScheme as useSystemColorScheme} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {Colors} from './Colors';

export type ThemeMode = 'light' | 'dark' | 'system';

interface ThemeContextProps {
    themeMode: ThemeMode;
    resolvedTheme: 'light' | 'dark';
    colors: typeof Colors.light;
    isDark: boolean;
    setThemeMode: (mode: ThemeMode) => Promise<void>;
}

const ThemeContext = createContext<ThemeContextProps | undefined>(undefined);

const THEME_STORAGE_KEY = 'app_theme_mode';

export function ThemeProvider({children}: { children: React.ReactNode }) {
    const [themeMode, setThemeModeState] = useState<ThemeMode>('system');
    const systemColorScheme = useSystemColorScheme();

    // Load the currently stored theme of the app
    useEffect(() => {
        const loadTheme = async () => {
            try {
                const storedTheme = await AsyncStorage.getItem(THEME_STORAGE_KEY);
                if (storedTheme === 'light' || storedTheme === 'dark' || storedTheme === 'system')
                    setThemeModeState(storedTheme);
            } catch (error) {
                console.error('Failed to load theme preference:', error);
            }
        };
        loadTheme();
    }, []);

    // Save theme selection and update state
    const setThemeMode = async (mode: ThemeMode) => {
        try {
            await AsyncStorage.setItem(THEME_STORAGE_KEY, mode);
            setThemeModeState(mode);
        } catch (error) {
            console.error('Failed to save theme preference:', error);
        }
    };

    // Resolve current theme active selection
    const resolvedTheme: 'light' | 'dark' =
        themeMode === 'system'
            ? (systemColorScheme === 'dark' ? 'dark' : 'light')
            : themeMode;

    const colors = Colors[resolvedTheme];
    const isDark = resolvedTheme === 'dark';

    return (
        <ThemeContext.Provider value={{themeMode, resolvedTheme, colors, isDark, setThemeMode}}>
            {children}
        </ThemeContext.Provider>
    );
}

// Get theme of the system
export function useTheme() {
    const context = useContext(ThemeContext);
    if (!context) {
        throw new Error('useTheme must be used within a ThemeProvider');
    }
    return context;
}
