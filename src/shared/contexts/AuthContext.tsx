import React, {createContext, useCallback, useContext, useEffect, useState} from "react";
import {User} from "@/src/shared/domain/User";
import {AuthRepository} from "@/src/shared/data/auth/AuthRepository";

interface AuthContextType {
    /** The signed in user, or null when nobody is signed in. */
    user: User | null;
    /** True while we are still asking Firebase about the session of the previous launch. */
    isRestoringSession: boolean;
    signInWithEmail: (email: string, password: string) => Promise<void>;
    signUpWithEmail: (email: string, password: string) => Promise<void>;
    signInWithGoogle: () => Promise<void>;
    sendPasswordResetEmail: (email: string) => Promise<void>;
    signOut: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

/**
 * Returns the account of the current user of the app and everything needed to change it.
 */
export function useAuth() {
    const context = useContext(AuthContext);
    if (context === undefined) throw new Error("useAuth must be used within an AuthProvider");
    return context;
}

export function AuthProvider({children}: { children: React.ReactNode }) {
    const [user, setUser] = useState<User | null>(null);
    const [isRestoringSession, setIsRestoringSession] = useState(true);

    useEffect(() => {
        // Firebase keeps the session of the previous launch, so this also signs the user back in silently.
        const unsubscribe = AuthRepository.observeAuthState(nextUser => {
            setUser(nextUser);
            setIsRestoringSession(false);
        });

        return unsubscribe;
    }, []);

    const signInWithEmail = useCallback(async (email: string, password: string) => {
        setUser(await AuthRepository.signInWithEmail(email, password));
    }, []);

    const signUpWithEmail = useCallback(async (email: string, password: string) => {
        setUser(await AuthRepository.signUpWithEmail(email, password));
    }, []);

    const signInWithGoogle = useCallback(async () => {
        const googleUser = await AuthRepository.signInWithGoogle();
        // A null user means the Google account picker was closed, so the current state should not change.
        if (googleUser) setUser(googleUser);
    }, []);

    const sendPasswordResetEmail = useCallback(async (email: string) => {
        await AuthRepository.sendPasswordResetEmail(email);
    }, []);

    const signOut = useCallback(async () => {
        await AuthRepository.signOut();
        setUser(null);
    }, []);

    return (
        <AuthContext.Provider
            value={{
                user,
                isRestoringSession,
                signInWithEmail,
                signUpWithEmail,
                signInWithGoogle,
                sendPasswordResetEmail,
                signOut
            }}>
            {children}
        </AuthContext.Provider>
    );
}
