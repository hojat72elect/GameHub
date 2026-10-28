import {
    GoogleAuthProvider,
    createUserWithEmailAndPassword,
    getAuth,
    onAuthStateChanged,
    sendPasswordResetEmail,
    signInWithCredential,
    signInWithEmailAndPassword,
    signOut,
    type Unsubscribe,
    type User,
} from "@react-native-firebase/auth";
import {GoogleSignin, statusCodes} from "@react-native-google-signin/google-signin";
import {AuthFailure} from "@/src/shared/data/auth/AuthFailure";

/**
 * This DataSource is the only place in the app that talks to Firebase Authentication.
 * The Firebase project itself comes from "android/app/google-services.json", so we only need to
 * talk to the default Firebase app here.
 *
 * This DataSource should only be used by the AuthRepository; and not directly in your UI.
 */
export class AuthRemoteDataSource {

    private static isGoogleSignInConfigured = false;

    /**
     * The Google sign in SDK needs the web client ID of the Firebase project (the "Web client" of the
     * Google provider in the Firebase console) before it can create a Firebase credential.
     */
    private static configureGoogleSignIn() {
        if (AuthRemoteDataSource.isGoogleSignInConfigured) return;

        const webClientId = process.env.EXPO_PUBLIC_GOOGLE_WEB_CLIENT_ID;
        if (!webClientId)
            throw new AuthFailure("configuration", "Google sign in is not configured. Please verify that EXPO_PUBLIC_GOOGLE_WEB_CLIENT_ID is defined in your .env file.");

        GoogleSignin.configure({webClientId});
        AuthRemoteDataSource.isGoogleSignInConfigured = true;
    }

    /**
     * Calls back right away with the user of the previous session (if any), and then on every
     * sign in/sign out. Returns a function that stops listening.
     */
    static observeAuthState(onUserChanged: (user: User | null) => void): Unsubscribe {
        return onAuthStateChanged(getAuth(), onUserChanged);
    }

    static async signInWithEmail(email: string, password: string): Promise<User> {
        const userCredential = await signInWithEmailAndPassword(getAuth(), email.trim(), password);
        return userCredential.user;
    }

    static async createAccountWithEmail(email: string, password: string): Promise<User> {
        const userCredential = await createUserWithEmailAndPassword(getAuth(), email.trim(), password);
        return userCredential.user;
    }

    static async sendPasswordResetEmail(email: string): Promise<void> {
        await sendPasswordResetEmail(getAuth(), email.trim());
    }

    /**
     * Returns null when the user closes the Google account picker without choosing an account.
     */
    static async signInWithGoogle(): Promise<User | null> {
        AuthRemoteDataSource.configureGoogleSignIn();

        await GoogleSignin.hasPlayServices({showPlayServicesUpdateDialog: true});

        const response = await GoogleSignin.signIn();
        if (response.type !== "success") return null;

        const idToken = response.data.idToken;
        if (!idToken) throw new AuthFailure("configuration", "Google did not return an ID token for the selected account.");

        const credential = GoogleAuthProvider.credential(idToken);
        const userCredential = await signInWithCredential(getAuth(), credential);
        return userCredential.user;
    }

    static async signOut(): Promise<void> {
        await signOut(getAuth());

        // Also forget the Google account, otherwise the account picker is skipped on the next attempt.
        if (!AuthRemoteDataSource.isGoogleSignInConfigured) return;

        try {
            await GoogleSignin.signOut();
        } catch (error) {
            console.error("Failed to clear the cached Google account:", error);
        }
    }

    /** Tells whether the user simply closed the Google account picker (which is not an error). */
    static isGoogleSignInCancelled(error: unknown): boolean {
        const code = (error as {code?: string} | null)?.code;
        return code === statusCodes.SIGN_IN_CANCELLED;
    }
}
