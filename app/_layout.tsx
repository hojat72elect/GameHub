import {Stack} from 'expo-router';
import {ThemeProvider} from '@/src/shared/contexts/ThemeContext';
import {LikesProvider} from "@/src/shared/contexts/LikesContext";
import {LanguageProvider} from "@/src/shared/contexts/LanguageContext";
import {AuthProvider} from "@/src/shared/contexts/AuthContext";
import '@/src/shared/internationalization';

export default function RootLayout() {
    return (
        <AuthProvider>{/*Firebase authentication of the user*/}
            <ThemeProvider>{/*Theme of the app*/}
                <LikesProvider>{/*Games that are liked by the user(only used if user is not logged in yet)*/}
                    <LanguageProvider>{/*Language of the app*/}
                        <Stack screenOptions={{headerShown: false}}/>
                    </LanguageProvider>
                </LikesProvider>
            </ThemeProvider>
        </AuthProvider>
    );
}
