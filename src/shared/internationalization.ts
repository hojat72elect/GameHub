import internationalization from 'i18next';
import {initReactI18next} from 'react-i18next';
import en from './locales/en.json';
import fa from './locales/fa.json';
import {Locale} from "expo-localization";

const languageResources = {
    en: {translation: en},
    fa: {translation: fa},
};

function getDeviceLanguage(): string {
    try {
        const Localization = require('expo-localization'); // we had to defer the import so the native module doesn't throw an error during initial load
        const locales: Locale[] = Localization.getLocales();
        const deviceLanguage = locales[0]?.languageCode || 'en';

        if (deviceLanguage === 'fa' || deviceLanguage === 'per') return 'fa'; // Persian code falls back "fa"

        return deviceLanguage;
    } catch (error) {
        console.error(`failed in getting device language : ${error}`);
        return 'en';
    }
}

internationalization
    .use(initReactI18next)
    .init({
        resources: languageResources,
        lng: getDeviceLanguage(),
        fallbackLng: 'en',
        interpolation: {
            escapeValue: false,
        },
    });

export default internationalization;
