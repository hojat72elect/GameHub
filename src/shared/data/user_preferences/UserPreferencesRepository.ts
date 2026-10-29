import type {UserPreferences} from "@/src/shared/domain/UserPreferences";
import {UserPreferencesRemoteDataSource} from "@/src/shared/data/user_preferences/UserPreferencesRemoteDataSource";

/**
 * This Repository is the access point for the preferences that belong to the account of the user
 * instead of the device: the liked games, the theme and the language.
 */
export class UserPreferencesRepository {

    /**
     * Decides what happens on sign in, and returns the preferences that the app has to show afterwards.
     *
     * - First sign in of an account (nothing is saved in Firebase yet): the preferences of the device
     *   become the preferences of the account.
     * - The account already exists: the preferences of the account win, and the device follows them.
     */
    static async syncOnSignIn(userId: string, preferencesOnDevice: UserPreferences): Promise<UserPreferences> {
        const preferencesOfAccount = await UserPreferencesRemoteDataSource.getPreferences(userId);

        if (!preferencesOfAccount) {
            await UserPreferencesRemoteDataSource.savePreferences(userId, preferencesOnDevice);
            return preferencesOnDevice;
        }

        return {...preferencesOnDevice, ...preferencesOfAccount};
    }

    /** Saves the preferences of the device to the account of the signed-in user. */
    static async savePreferences(userId: string, preferences: UserPreferences): Promise<void> {
        await UserPreferencesRemoteDataSource.savePreferences(userId, preferences);
    }
}
