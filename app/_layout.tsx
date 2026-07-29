import {Stack} from 'expo-router';
import {ThemeProvider} from '@/src/ThemeContext';
import {LikesProvider} from "@/src/feature_likes/LikesContext";

export default function RootLayout() {
    return (
        <ThemeProvider> // The information about the theme of the app
            <LikesProvider> // The information about the games that are liked by the user
                <Stack screenOptions={{headerShown: false}}/>
            </LikesProvider>
        </ThemeProvider>
    );
}
