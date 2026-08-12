import {GameDetails} from "@/src/shared/domain/GameDetails";
import {getDatabase} from "@/src/shared/data/local/Database";

export class GamesDetailedLocalDataSource {
    /**
     * Cache duration in milliseconds (24 hours)
     */
    private static readonly CACHE_DURATION = 24 * 60 * 60 * 1000;

    /**
     * Save game details to the local database
     */
    static async saveGameDetails(gameDetails: GameDetails): Promise<void> {
        const db = await getDatabase();
        const cachedAt = Date.now();

        await db.runAsync(
            `INSERT
            OR REPLACE INTO games_detailed 
            (id, name, cover_id, cover_image_id, artworks, first_release_date, involved_companies, 
             videos, screenshots, websites, similar_games, summary, genres, platforms, 
             game_modes, player_perspectives, themes, cached_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
            [
                gameDetails.id,
                gameDetails.name,
                gameDetails.cover?.id || null,
                gameDetails.cover?.image_id || null,
                JSON.stringify(gameDetails.artworks || []),
                gameDetails.first_release_date || null,
                JSON.stringify(gameDetails.involved_companies || []),
                JSON.stringify(gameDetails.videos || []),
                JSON.stringify(gameDetails.screenshots || []),
                JSON.stringify(gameDetails.websites || []),
                JSON.stringify(gameDetails.similar_games || []),
                gameDetails.summary || null,
                JSON.stringify(gameDetails.genres || []),
                JSON.stringify(gameDetails.platforms || []),
                JSON.stringify(gameDetails.game_modes || []),
                JSON.stringify(gameDetails.player_perspectives || []),
                JSON.stringify(gameDetails.themes || []),
                cachedAt
            ]
        );
    }

    /**
     * Get game details by ID from local database
     */
    static async getGameDetailsById(gameId: number): Promise<GameDetails | null> {
        const db = await getDatabase();
        const now = Date.now();
        const cacheThreshold = now - this.CACHE_DURATION;

        const row = await db.getFirstAsync<any>(
            `SELECT *
             FROM games_detailed
             WHERE id = ?
               AND cached_at > ?`,
            [gameId, cacheThreshold]
        );

        if (!row) {
            return null;
        }

        return {
            id: row.id,
            name: row.name,
            cover: row.cover_id ? {
                id: row.cover_id,
                image_id: row.cover_image_id
            } : undefined,
            artworks: JSON.parse(row.artworks || '[]'),
            first_release_date: row.first_release_date,
            involved_companies: JSON.parse(row.involved_companies || '[]'),
            videos: JSON.parse(row.videos || '[]'),
            screenshots: JSON.parse(row.screenshots || '[]'),
            websites: JSON.parse(row.websites || '[]'),
            similar_games: JSON.parse(row.similar_games || '[]'),
            summary: row.summary,
            genres: JSON.parse(row.genres || '[]'),
            platforms: JSON.parse(row.platforms || '[]'),
            game_modes: JSON.parse(row.game_modes || '[]'),
            player_perspectives: JSON.parse(row.player_perspectives || '[]'),
            themes: JSON.parse(row.themes || '[]'),
        } as GameDetails;
    }

    /**
     * Get multiple game details by their IDs
     */
    static async getGamesDetailsByIds(gameIds: number[]): Promise<GameDetails[]> {
        if (gameIds.length === 0) return [];

        const db = await getDatabase();
        const now = Date.now();
        const cacheThreshold = now - this.CACHE_DURATION;

        const placeholders = gameIds.map(() => '?').join(',');
        const rows = await db.getAllAsync<any>(
            `SELECT *
             FROM games_detailed
             WHERE id IN (${placeholders})
               AND cached_at > ?`,
            [...gameIds, cacheThreshold]
        );

        return rows.map(row => ({
            id: row.id,
            name: row.name,
            cover: row.cover_id ? {
                id: row.cover_id,
                image_id: row.cover_image_id
            } : undefined,
            artworks: JSON.parse(row.artworks || '[]'),
            first_release_date: row.first_release_date,
            involved_companies: JSON.parse(row.involved_companies || '[]'),
            videos: JSON.parse(row.videos || '[]'),
            screenshots: JSON.parse(row.screenshots || '[]'),
            websites: JSON.parse(row.websites || '[]'),
            similar_games: JSON.parse(row.similar_games || '[]'),
            summary: row.summary,
            genres: JSON.parse(row.genres || '[]'),
            platforms: JSON.parse(row.platforms || '[]'),
            game_modes: JSON.parse(row.game_modes || '[]'),
            player_perspectives: JSON.parse(row.player_perspectives || '[]'),
            themes: JSON.parse(row.themes || '[]'),
        } as GameDetails));
    }

    /**
     * Clear old cache entries
     */
    static async clearOldCache(): Promise<void> {
        const db = await getDatabase();
        const now = Date.now();
        const cacheThreshold = now - this.CACHE_DURATION;

        await db.runAsync(
            `DELETE
             FROM games_detailed
             WHERE cached_at <= ?`,
            [cacheThreshold]
        );
    }
}
