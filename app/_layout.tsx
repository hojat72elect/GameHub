import {Stack} from 'expo-router';
import {ThemeProvider} from '@/src/ThemeContext';
import {LikesProvider} from "@/src/feature_likes/LikesContext";
import {LanguageProvider} from "@/src/LanguageContext";
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
