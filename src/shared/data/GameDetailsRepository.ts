import {GameDetails} from "@/src/shared/domain/GameDetails";
import {GameDetailsLocalDataSource} from "@/src/shared/data/local/datasources/GameDetailsLocalDataSource";
import {GameDetailsRemoteDataSource} from "@/src/shared/data/remote/GameDetailsRemoteDataSource";
import {getDatabase} from "@/src/shared/data/local/Database";

/**
 * This repository is the access point for getting very detailed info about a game.
 */
export class GameDetailsRepository {
    /**
     * gets detailed info about just one game.
     */
    static async getGameDetailsById(gameId: number): Promise<GameDetails> {
        // Check if we have the game in local cache
        const cachedGameDetails = await GameDetailsLocalDataSource.getGameDetailsById(gameId);
        if (cachedGameDetails) return cachedGameDetails;

        // fetch it from remote API
        const remoteGameDetails = await GameDetailsRemoteDataSource.getGameDetailsById(gameId.toString());

        // Save to local cache
        await GameDetailsLocalDataSource.saveGameDetails(remoteGameDetails);

        // Clear old cache entries
        await GameDetailsRepository.clearOldCache();

        return remoteGameDetails;
    }

    /**
     * warn : Keep the usage of this function to a minimum because it is very resource heavy.
     * It tries to get extensive info about multiple games at the same time.
     */
    static async getGamesDetailsByIds(gameIds: number[]): Promise<GameDetails[]> {
        if (gameIds.length === 0) return [];

        // Check if we have all those games in local cache (Almost always we do)
        const cachedGames = await GameDetailsLocalDataSource.getGamesDetailsByIds(gameIds);
        const cachedIds = new Set(cachedGames.map(gameDetails => gameDetails.id));
        const missingIds = gameIds.filter(id => !cachedIds.has(id));

        if (missingIds.length === 0) return cachedGames;

        // Fetch the missing games from remote API
        const remoteGames = await GameDetailsRemoteDataSource.getGamesDetailsByIds(missingIds.map(id => id.toString()));

        // Save fetched games to local cache
        for (const game of remoteGames) {
            await GameDetailsLocalDataSource.saveGameDetails(game);
        }

        // Clear old cache entries
        await GameDetailsRepository.clearOldCache();

        // Combine cached and remote games
        const allGames = [...cachedGames, ...remoteGames];

        // Return in the order of requested IDs
        const gameMap = new Map(allGames.map(g => [g.id, g]));
        return gameIds.map(id => gameMap.get(id)).filter((g): g is GameDetails => g !== undefined);
    }

    /**
     * Clear old cache entries.
     *
     * todo : Is it really necessary to clear my old cache? Wouldn't it be better for the loading time
     * if I just let it be?
     */
    private static async clearOldCache() {

        const db = await getDatabase();
        const now = Date.now();
        const cacheThreshold = now - GameDetailsLocalDataSource.CACHE_DURATION;

        await db.runAsync(
            `DELETE
             FROM games_detailed
             WHERE cached_at <= ?`,
            [cacheThreshold]
        );
    }
}
