import type {User} from "@react-native-firebase/auth";
import {User as AppUser} from "@/src/shared/domain/User";
import {AuthRemoteDataSource} from "@/src/shared/data/auth/AuthRemoteDataSource";
import type {AuthFailureReason} from "@/src/shared/data/auth/AuthFailure";
import {AuthFailure} from "@/src/shared/data/auth/AuthFailure";

export {AuthFailure};
export type {AuthFailureReason};

const getFailureReason = (code: string): AuthFailureReason | undefined => {
    switch (code) {
        case "auth/invalid-email":
        case "auth/missing-email":
            return "invalidEmail";
        case "auth/missing-password":
            return "missingPassword";
        case "auth/wrong-password":
        case "auth/user-not-found":
        case "auth/invalid-credential":
        case "auth/invalid-login-credentials":
            return "wrongCredentials";
        case "auth/email-already-in-use":
            return "emailAlreadyInUse";
        case "auth/weak-password":
            return "weakPassword";
        case "auth/user-disabled":
            return "userDisabled";
        case "auth/operation-not-allowed":
            return "providerDisabled";
        case "auth/too-many-requests":
            return "tooManyRequests";
        case "auth/network-request-failed":
            return "network";
        case "PLAY_SERVICES_NOT_AVAILABLE":
            return "playServicesUnavailable";
        case "DEVELOPER_ERROR":
        case "SIGN_IN_REQUIRED":
        case "NULL_PRESENTER":
            return "configuration";
        default:
            return undefined;
    }
};

/**
 * This Repository is the access point for everything about the account of the user:
 * signing in/up, resetting the password and signing out.
 * The errors it throws are always an AuthFailure, so the UI can show a translated message.
 */
export class AuthRepository {

    static observeAuthState(onUserChanged: (user: AppUser | null) => void): () => void {
        return AuthRemoteDataSource.observeAuthState(
            firebaseUser => onUserChanged(firebaseUser ? AuthRepository.toAppUser(firebaseUser) : null)
        );
    }

    static async signInWithEmail(email: string, password: string): Promise<AppUser> {
        const user = await AuthRepository.execute(() => AuthRemoteDataSource.signInWithEmail(email, password));
        return AuthRepository.toAppUser(user);
    }

    static async signUpWithEmail(email: string, password: string): Promise<AppUser> {
        const user = await AuthRepository.execute(() => AuthRemoteDataSource.createAccountWithEmail(email, password));
        return AuthRepository.toAppUser(user);
    }

    /**
     * Returns null when the user closes the Google account picker without choosing an account.
     */
    static async signInWithGoogle(): Promise<AppUser | null> {
        const user = await AuthRepository.execute(() => AuthRemoteDataSource.signInWithGoogle());
        return user ? AuthRepository.toAppUser(user) : null;
    }

    static async sendPasswordResetEmail(email: string): Promise<void> {
        await AuthRepository.execute(() => AuthRemoteDataSource.sendPasswordResetEmail(email));
    }

    static async signOut(): Promise<void> {
        await AuthRepository.execute(() => AuthRemoteDataSource.signOut());
    }

    private static async execute<T>(action: () => Promise<T>): Promise<T> {
        try {
            return await action();
        } catch (error) {
            throw AuthRepository.toAuthFailure(error);
        }
    }

    private static toAuthFailure(error: unknown): AuthFailure {
        if (error instanceof AuthFailure) return error;

        // The Google account picker being closed is not a failure; the UI must stay quiet about it.
        if (AuthRemoteDataSource.isGoogleSignInCancelled(error)) return new AuthFailure("cancelled");

        const code = (error as { code?: unknown } | null)?.code;
        const message = error instanceof Error ? error.message : undefined;

        if (typeof code !== "string")
            return new AuthFailure("unknown", message);

        return new AuthFailure(getFailureReason(code) ?? "unknown", message);
    }

    private static toAppUser(user: User): AppUser {
        return {
            id: user.uid,
            email: user.email,
            displayName: user.displayName,
            photoUrl: user.photoURL,
            isEmailVerified: user.emailVerified,
            signInProviders: (user.providerData ?? []).map(provider => provider.providerId),
        };
    }
}
