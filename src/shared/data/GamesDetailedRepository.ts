import {GameDetails} from "@/src/shared/domain/GameDetails";
import {GamesDetailedLocalDataSource} from "@/src/shared/data/local/datasources/GamesDetailedLocalDataSource";
import {getGameDetailsByIdUseCase} from "@/src/shared/data/remote/getGameDetailsByIdUseCase";
import {getGamesByIdsUseCase} from "@/src/shared/getGamesByIdsUseCase";

/**
 * This repository is the access point for getting very detailed info about a game.
 */
export class GamesDetailedRepository {
    /**
     * gets detailed info about just one game.
     */
    static async getGameDetailsById(gameId: number): Promise<GameDetails> {
        // Try to get from local cache first
        const cachedGameDetails = await GamesDetailedLocalDataSource.getGameDetailsById(gameId);
        if (cachedGameDetails) {
            return cachedGameDetails;
        }

        // Otherwise, fetch from remote API
        const remoteGameDetails = await getGameDetailsByIdUseCase(gameId.toString());

        // Save to local cache
        await GamesDetailedLocalDataSource.saveGameDetails(remoteGameDetails);

        // Clear old cache entries
        await GamesDetailedLocalDataSource.clearOldCache();

        return remoteGameDetails;
    }

    /**
     * warn : Keep the usage of this function to a minimum because it is very resource heavy.
     * It tries to get extensive info about multiple games at the same time.
     */
    static async getGamesDetailsByIds(gameIds: number[]): Promise<GameDetails[]> {
        if (gameIds.length === 0) return [];

        // Try to get from local cache first
        const cachedGames = await GamesDetailedLocalDataSource.getGamesDetailsByIds(gameIds);

        // Check if we have all games in cache
        const cachedIds = new Set(cachedGames.map(g => g.id));
        const missingIds = gameIds.filter(id => !cachedIds.has(id));

        if (missingIds.length === 0) {
            return cachedGames;
        }

        // Fetch missing games from remote API
        const remoteGames = await getGamesByIdsUseCase(missingIds.map(id => id.toString()));

        // Save fetched games to local cache
        for (const game of remoteGames) {
            await GamesDetailedLocalDataSource.saveGameDetails(game);
        }

        // Clear old cache entries
        await GamesDetailedLocalDataSource.clearOldCache();

        // Combine cached and remote games
        const allGames = [...cachedGames, ...remoteGames];

        // Return in the order of requested IDs
        const gameMap = new Map(allGames.map(g => [g.id, g]));
        return gameIds.map(id => gameMap.get(id)).filter((g): g is GameDetails => g !== undefined);
    }
}
