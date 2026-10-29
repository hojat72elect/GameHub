import React, {useEffect, useRef} from "react";
import {useAuth} from "@/src/shared/contexts/AuthContext";
import {useTheme} from "@/src/shared/contexts/ThemeContext";
import {useLanguage} from "@/src/shared/contexts/LanguageContext";
import {useLikes} from "@/src/shared/contexts/LikesContext";
import {UserPreferencesRepository} from "@/src/shared/data/user_preferences/UserPreferencesRepository";
import type {UserPreferences} from "@/src/shared/domain/UserPreferences";

const areSameGameIds = (first: string[], second: string[]): boolean => {
    const gameIds = new Set(second);
    return first.length === gameIds.size && first.every(gameId => gameIds.has(gameId));
};

const areSamePreferences = (first: UserPreferences, second: UserPreferences): boolean => {
    return first.themeMode === second.themeMode
        && first.language === second.language
        && areSameGameIds(first.likedGameIds, second.likedGameIds);
};

/**
 * Keeps the preferences that belong to the account of the user in sync with the device:
 * the liked games, the theme and the language.
 *
 * - The first time an account signs in, the preferences of the device are saved to that account.
 * - Every next sign in (on this or any other device), the preferences of the account are applied.
 * - Every change the user makes while he is signed in is saved to his account.
 *
 * This provider has no value of its own: it is the only place that knows about both the account and
 * the three providers that own those preferences, so it must be nested inside all of them.
 */
export function UserPreferencesSyncProvider({children}: { children: React.ReactNode }) {
    const {user, isRestoringSession} = useAuth();
    const {themeMode, setThemeMode, isHydrated: isThemeHydrated} = useTheme();
    const {language, setLanguage, isHydrated: isLanguageHydrated} = useLanguage();
    const {likedGameIds, replaceLikedGameIds, isHydrated: areLikesHydrated} = useLikes();

    const userId = user?.id ?? null;
    const isHydrated = isThemeHydrated && isLanguageHydrated && areLikesHydrated;

    /** The account whose preferences are already synced with this device. */
    const syncedUserId = useRef<string | null>(null);
    /** The preferences that the device and the account already agree on. */
    const syncedPreferences = useRef<UserPreferences | null>(null);

    // Sign in and sign out: the account wins, unless it has no preferences yet (first sign in).
    useEffect(() => {
        if (!userId) {
            syncedUserId.current = null;
            syncedPreferences.current = null;
            return;
        }

        // The preferences of the device must be complete before they can be compared or saved.
        if (isRestoringSession || !isHydrated) return;
        if (syncedUserId.current === userId) return;

        const preferencesOnDevice: UserPreferences = {likedGameIds: [...likedGameIds], themeMode, language};
        let isCancelled = false;

        const syncPreferences = async () => {
            try {
                const preferencesOfAccount = await UserPreferencesRepository.syncOnSignIn(userId, preferencesOnDevice);
                if (isCancelled) return;

                // Those values are already on both sides, so they must not be sent back to the account.
                syncedPreferences.current = preferencesOfAccount;

                // The setters apply the preferences of the account to the app and to the device.
                if (preferencesOfAccount.themeMode !== themeMode)
                    await setThemeMode(preferencesOfAccount.themeMode);

                if (preferencesOfAccount.language !== language)
                    await setLanguage(preferencesOfAccount.language);

                if (!areSameGameIds(preferencesOfAccount.likedGameIds, preferencesOnDevice.likedGameIds))
                    replaceLikedGameIds(preferencesOfAccount.likedGameIds);
            } catch (error) {
                console.error("Failed to sync the preferences of the account:", error);
            } finally {
                if (!isCancelled) syncedUserId.current = userId;
            }
        };

        syncPreferences();

        return () => {
            isCancelled = true;
        };
        // The preferences are read only when the sync starts, so a change of one of them does not
        // restart the sign in flow; the effect below is the one that sends those changes to the account.
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [userId, isRestoringSession, isHydrated]);

    // Every change that the user makes while he is signed in is saved to his account.
    useEffect(() => {
        if (!userId || syncedUserId.current !== userId) return;

        const preferencesOnDevice: UserPreferences = {likedGameIds: [...likedGameIds], themeMode, language};

        const preferencesOfAccount = syncedPreferences.current;
        if (preferencesOfAccount && areSamePreferences(preferencesOfAccount, preferencesOnDevice)) return;

        syncedPreferences.current = preferencesOnDevice;

        UserPreferencesRepository.savePreferences(userId, preferencesOnDevice).catch(error => {
            console.error("Failed to save the preferences to the account:", error);
        });
    }, [userId, themeMode, language, likedGameIds]);

    return <>{children}</>;
}
