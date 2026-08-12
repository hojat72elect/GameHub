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

        // Get it from remote API and save it to cache
        const remoteGameDetails = await GameDetailsRemoteDataSource.getGameDetailsById(gameId.toString());
        await GameDetailsLocalDataSource.saveGameDetails(remoteGameDetails);

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

        // Fetch the missing games from remote API and then save them to local cache
        const remoteGames = await GameDetailsRemoteDataSource.getGamesDetailsByIds(missingIds.map(id => id.toString()));
        for (const game of remoteGames) {
            await GameDetailsLocalDataSource.saveGameDetails(game);
        }

        await GameDetailsRepository.clearOldCache();

        const allGames = [...cachedGames, ...remoteGames];
        return allGames.filter((gameDetails): gameDetails is GameDetails => gameDetails !== undefined);
    }

    /**
     * Clear old cache entries.
     * With each entry to the database, we have saved the time this game was saved to DB.
     * So, when the life of the cache is ended, it will be deleted so new info can be fetched from remote API.
     *
     * todo : It's an absolute anti pattern to invalidate old cache only after user has requested info about it.
     * We need to have an asynchronous timer that fires this function periodically.
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
