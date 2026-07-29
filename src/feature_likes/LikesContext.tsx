import React, {createContext, useCallback, useContext, useEffect, useState} from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";

const LIKES_STORAGE_KEY = "gamehub_liked_games";

const LikesContext = createContext<{
    likedGameIds: Set<string>;
    getLikedGames: (gameId: string) => boolean;
    toggleLikedGames: (gameId: string) => boolean;
}>({
    likedGameIds: new Set(),
    getLikedGames: () => false,
    toggleLikedGames: () => false,
});

export function LikesProvider({children}: { children: React.ReactNode }) {
    const [likedGameIds, setLikedGameIds] = useState<Set<string>>(new Set());

    useEffect(() => {
        const loadLikes = async () => {
            try {
                const stored = await AsyncStorage.getItem(LIKES_STORAGE_KEY);
                if (stored) {
                    setLikedGameIds(new Set(JSON.parse(stored)));
                }
            } catch (err) {
                console.error("Error loading liked games:", err);
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

    return (
        <LikesContext.Provider value={{likedGameIds, getLikedGames: isLiked, toggleLikedGames: toggleLike}}>
            {children}
        </LikesContext.Provider>
    );
}

export function useLikes() {
    return useContext(LikesContext);
}
