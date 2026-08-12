import {Stack} from 'expo-router';
import {ThemeProvider} from '@/src/shared/contexts/ThemeContext';
import {LikesProvider} from "@/src/shared/contexts/LikesContext";
import {LanguageProvider} from "@/src/shared/contexts/LanguageContext";
import '@/src/shared/internationalization';

export default function RootLayout() {
    return (
        <ThemeProvider>{/*The information about the theme of the app*/}
            <LikesProvider>{/*The information about the games that are liked by the user*/}
                <LanguageProvider>
                    <Stack screenOptions={{headerShown: false}}/>
                </LanguageProvider>
            </LikesProvider>
        </ThemeProvider>
    );
}
