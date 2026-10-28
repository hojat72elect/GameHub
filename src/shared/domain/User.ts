/**
 * The currently signed-in user of the app.
 */
export interface User {
    id: string;
    email: string | null;
    displayName: string | null;
    photoUrl: string | null;
    isEmailVerified: boolean;
    // The sign in methods of the account (e.g. "password" or "google").
    signInProviders: string[];
}
