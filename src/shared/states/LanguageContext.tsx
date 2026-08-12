import React, {createContext, useCallback, useContext, useEffect, useState} from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import internationalization from '@/src/shared/internationalization';
import {AppState, I18nManager} from 'react-native';
import {Locale} from "expo-localization";
import {Language} from "@/src/shared/domain/Language";

interface LanguageContextType {
    language: Language;
    setLanguage: (language: Language) => void;
    isRTL: boolean;
}

const LanguageContext = createContext<LanguageContextType | undefined>(undefined);

/**
 * Returns the current language of the app.
 */
export function useLanguage() {
    const context = useContext(LanguageContext);
    if (context === undefined) throw new Error('useLanguage must be used within a LanguageProvider');
    return context;
}

const LANGUAGE_STORAGE_KEY = '@gamehub_language';

const getDeviceLanguage = (): 'en' | 'fa' => {
    try {
        const Localization = require('expo-localization'); // I had to defer this import because of some error on Android
        const locales:Locale[] = Localization.getLocales();
        const deviceLanguage = locales[0]?.languageCode || 'en';

        if (deviceLanguage === 'fa' || deviceLanguage === 'per') return 'fa';

        return deviceLanguage as 'en' | 'fa';
    } catch (error) {
        console.error(`Failed to get the current language of the system : ${error}`);
        return 'en';
    }
};

export function LanguageProvider({children}: { children: React.ReactNode }) {
    const [language, setLanguageState] = useState<Language>(getDeviceLanguage());
    const [isRTL, setIsRTL] = useState<boolean>(language === 'fa');

    const getEffectiveLanguage = (lang: Language): 'en' | 'fa' => {
        if (lang === 'system') {
            return getDeviceLanguage();
        }
        return lang;
    };

    const setLanguage = useCallback(async (newLanguage: Language) => {
        try {
            await AsyncStorage.setItem(LANGUAGE_STORAGE_KEY, newLanguage);
            setLanguageState(newLanguage);
            const effectiveLanguage = getEffectiveLanguage(newLanguage);
            await internationalization.changeLanguage(effectiveLanguage);

            // Handle RTL for Persian
            const shouldBeRTL = effectiveLanguage === 'fa';
            if (I18nManager.isRTL !== shouldBeRTL) {
                I18nManager.allowRTL(shouldBeRTL);
                I18nManager.forceRTL(shouldBeRTL);
            }
            setIsRTL(shouldBeRTL);
        } catch (error) {
            console.error('Failed to save language preference:', error);
        }
    }, []);

    useEffect(() => {
        loadLanguagePreference();
    }, []);

    useEffect(() => {
        const subscription = AppState.addEventListener('change', async (nextAppState) => {
            if (nextAppState === 'active') {
                // Check if device language changed when app returns to foreground
                const savedLanguage = await AsyncStorage.getItem(LANGUAGE_STORAGE_KEY);
                if (savedLanguage === 'system') {
                    // If system language is selected, update to current device language
                    const deviceLanguage = getDeviceLanguage();
                    await internationalization.changeLanguage(deviceLanguage);
                    const shouldBeRTL = deviceLanguage === 'fa';
                    if (I18nManager.isRTL !== shouldBeRTL) {
                        I18nManager.allowRTL(shouldBeRTL);
                        I18nManager.forceRTL(shouldBeRTL);
                    }
                    setIsRTL(shouldBeRTL);
                } else if (!savedLanguage) {
                    // Only auto-update if user hasn't manually set a preference
                    const deviceLanguage = getDeviceLanguage();
                    setLanguageState(currentLanguage => {
                        if (deviceLanguage !== currentLanguage) {
                            setLanguage(deviceLanguage);
                            return deviceLanguage;
                        }
                        return currentLanguage;
                    });
                }
            }
        });

        return () => {
            subscription.remove();
        };
    }, [setLanguage]);

    const loadLanguagePreference = async () => {
        try {
            const savedLanguage = await AsyncStorage.getItem(LANGUAGE_STORAGE_KEY);
            if (savedLanguage === 'en' || savedLanguage === 'fa') {
                await setLanguage(savedLanguage);
            }
        } catch (error) {
            console.error('Failed to load language preference:', error);
        }
    };

    return (
        <LanguageContext.Provider value={{language, setLanguage, isRTL}}>
            {children}
        </LanguageContext.Provider>
    );
}
