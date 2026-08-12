import {Game} from "@/src/shared/domain/Game";
import {GamesCategory} from "@/src/shared/domain/GamesCategory";
import {GamesGeneralLocalDataSource} from "@/src/shared/data/local/datasources/GamesGeneralLocalDataSource";
import {GamesGeneralRemoteDataSource} from "@/src/shared/data/remote/GamesGeneralRemoteDataSource";
import {GamesCategoryDatasource} from "@/src/shared/data/remote/GamesCategoryDatasource";

/**
 * This Repository is the access point for getting general (bare minimum) info about multiple games.
 * By using this repo, we only want to get the games' IDs and screenshots (nothing more!).
 */
export class GamesGeneralRepository {

    static async getDiscoverScreenGames(): Promise<{
        popularGames: Game[];
        recentlyReleasedGames: Game[];
        comingSoonGames: Game[];
        mostAnticipatedGames: Game[];
    }> {
        // Try to get from local cache first
        const popularGames = await GamesGeneralLocalDataSource.getGamesByCategory('popular');
        const recentlyReleasedGames = await GamesGeneralLocalDataSource.getGamesByCategory('recent');
        const comingSoonGames = await GamesGeneralLocalDataSource.getGamesByCategory('soon');
        const mostAnticipatedGames = await GamesGeneralLocalDataSource.getGamesByCategory('anticipated');

        // If all categories have cached data, return it
        if (popularGames && recentlyReleasedGames && comingSoonGames && mostAnticipatedGames) {
            return {
                popularGames,
                recentlyReleasedGames,
                comingSoonGames,
                mostAnticipatedGames
            };
        }

        // Otherwise, fetch from remote API
        const remoteData = await GamesGeneralRemoteDataSource.get();

        // Save to local cache
        await GamesGeneralLocalDataSource.saveGames(remoteData.popularGames, 'popular');
        await GamesGeneralLocalDataSource.saveGames(remoteData.recentlyReleasedGames, 'recent');
        await GamesGeneralLocalDataSource.saveGames(remoteData.comingSoonGames, 'soon');
        await GamesGeneralLocalDataSource.saveGames(remoteData.mostAnticipatedGames, 'anticipated');

        // Clear old cache entries
        await GamesGeneralLocalDataSource.clearOldCache();

        return remoteData;
    }

    static async getCategoryScreenGames(category: GamesCategory): Promise<Game[]> {
        const categoryMap: Record<GamesCategory, string> = {
            [GamesCategory.Popular]: 'popular',
            [GamesCategory.RecentlyReleased]: 'recent',
            [GamesCategory.ComingSoon]: 'soon',
            [GamesCategory.MostAnticipated]: 'anticipated'
        };

        const cacheKey = categoryMap[category];

        // Try to get from local cache first
        const cachedGames = await GamesGeneralLocalDataSource.getGamesByCategory(cacheKey);
        if (cachedGames) {
            return cachedGames;
        }

        // Otherwise, fetch from remote API
        const remoteGames = await GamesCategoryDatasource.get(category);

        // Save to local cache
        await GamesGeneralLocalDataSource.saveGames(remoteGames, cacheKey);

        // Clear old cache entries
        await GamesGeneralLocalDataSource.clearOldCache();

        return remoteGames;
    }
}
