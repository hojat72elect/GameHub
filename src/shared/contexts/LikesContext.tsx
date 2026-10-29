import React, {createContext, useCallback, useContext, useEffect, useState} from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";

const LIKES_STORAGE_KEY = "gamehub_liked_games";

interface LikesContextType {
    likedGameIds: Set<string>;
    getLikedGames: (gameId: string) => boolean;
    toggleLikedGames: (gameId: string) => boolean;
    /** Replaces the liked games of the device with the liked games of the account of the user. */
    replaceLikedGameIds: (gameIds: string[]) => void;
    /** False while the liked games of the previous launch are still being read from the device. */
    isHydrated: boolean;
}

const LikesContext = createContext<LikesContextType>({
    likedGameIds: new Set(),
    getLikedGames: () => false,
    toggleLikedGames: () => false,
    replaceLikedGameIds: () => undefined,
    isHydrated: false,
});

export function LikesProvider({children}: { children: React.ReactNode }) {
    const [likedGameIds, setLikedGameIds] = useState<Set<string>>(new Set());
    const [isHydrated, setIsHydrated] = useState<boolean>(false);

    useEffect(() => {
        const loadLikes = async () => {
            try {
                const stored = await AsyncStorage.getItem(LIKES_STORAGE_KEY);
                if (stored) {
                    setLikedGameIds(new Set(JSON.parse(stored)));
                }
            } catch (err) {
                console.error("Error loading liked games:", err);
            } finally {
                setIsHydrated(true);
            }
        };
        loadLikes();
    }, []);

    const persistLikes = useCallback(async (ids: Set<string>) => {
        try {
            await AsyncStorage.setItem(LIKES_STORAGE_KEY, JSON.stringify([...ids]));
        } catch (err) {
            console.error("Error saving liked games:", err);
        }
    }, []);

    const isLiked = useCallback((gameId: string) => {
        return likedGameIds.has(gameId);
    }, [likedGameIds]);

    const toggleLike = useCallback((gameId: string): boolean => {
        let newLiked: boolean;
        setLikedGameIds(prev => {
            const next = new Set(prev);
            if (next.has(gameId)) {
                next.delete(gameId);
                newLiked = false;
            } else {
                next.add(gameId);
                newLiked = true;
            }
            persistLikes(next);
            return next;
        });
        return newLiked!;
    }, [persistLikes]);

    const replaceLikedGameIds = useCallback((gameIds: string[]) => {
        const next = new Set(gameIds);
        setLikedGameIds(next);
        persistLikes(next);
    }, [persistLikes]);

    return (
        <LikesContext.Provider
            value={{likedGameIds, getLikedGames: isLiked, toggleLikedGames: toggleLike, replaceLikedGameIds, isHydrated}}>
            {children}
        </LikesContext.Provider>
    );
}

export function useLikes() {
    return useContext(LikesContext);
}
