import {Stack} from 'expo-router';
import {ThemeProvider} from '@/src/shared/contexts/ThemeContext';
import {LikesProvider} from "@/src/shared/contexts/LikesContext";
import {LanguageProvider} from "@/src/shared/contexts/LanguageContext";
import {AuthProvider} from "@/src/shared/contexts/AuthContext";
import {UserPreferencesSyncProvider} from "@/src/shared/contexts/UserPreferencesSyncContext";
import '@/src/shared/internationalization';

export default function RootLayout() {
    return (
        <AuthProvider>{/*Firebase authentication of the user*/}
            <ThemeProvider>{/*Theme of the app*/}
                <LikesProvider>{/*Games that are liked by the user*/}
                    <LanguageProvider>{/*Language of the app*/}
                        <UserPreferencesSyncProvider>{/*Syncs the likes, the theme and the language with the account*/}
                            <Stack screenOptions={{headerShown: false}}/>
                        </UserPreferencesSyncProvider>
                    </LanguageProvider>
                </LikesProvider>
            </ThemeProvider>
        </AuthProvider>
    );
}
