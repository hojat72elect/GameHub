import {collection, doc, getDoc, getFirestore, serverTimestamp, setDoc} from "@react-native-firebase/firestore";
import type {UserPreferences} from "@/src/shared/domain/UserPreferences";

const USERS_COLLECTION = "users";

/**
 * This DataSource is the only place in the app that talks to Cloud Firestore.
 *
 * Every account owns exactly one document: "users/<uid>". Its fields are the preferences of that
 * account (the liked games, the theme and the language), so they follow the user between devices.
 *
 * This DataSource should only be used by the UserPreferencesRepository; and not directly in your UI.
 */
export class UserPreferencesRemoteDataSource {

    private static preferencesDocument(userId: string) {
        return doc(collection(getFirestore(), USERS_COLLECTION), userId);
    }

    /**
     * Returns null when the account has never saved its preferences, which is how the first sign in
     * of a user on any device is recognized. Fields that are missing or invalid in the document are
     * left out, so the caller keeps what it already has for them.
     */
    static async getPreferences(userId: string): Promise<Partial<UserPreferences> | null> {
        const snapshot = await getDoc(UserPreferencesRemoteDataSource.preferencesDocument(userId));
        if (!snapshot.exists()) return null;

        return UserPreferencesRemoteDataSource.toUserPreferences(snapshot.data());
    }

    static async savePreferences(userId: string, preferences: UserPreferences): Promise<void> {
        await setDoc(UserPreferencesRemoteDataSource.preferencesDocument(userId), {
            likedGameIds: preferences.likedGameIds,
            themeMode: preferences.themeMode,
            language: preferences.language,
            updatedAt: serverTimestamp(),
        });
    }

    private static toUserPreferences(data: Record<string, unknown> | undefined): Partial<UserPreferences> {
        const preferences: Partial<UserPreferences> = {};

        const likedGameIds = data?.likedGameIds;
        if (Array.isArray(likedGameIds))
            preferences.likedGameIds = likedGameIds.filter((gameId): gameId is string => typeof gameId === "string");

        const themeMode: unknown = data?.themeMode;
        if (themeMode === "light" || themeMode === "dark" || themeMode === "system")
            preferences.themeMode = themeMode;

        const language: unknown = data?.language;
        if (language === "en" || language === "fa" || language === "system")
            preferences.language = language;

        return preferences;
    }
}
