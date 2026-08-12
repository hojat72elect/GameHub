import {GameDetails} from "@/src/shared/domain/GameDetails";
import {getDatabase} from "@/src/shared/data/local/Database";

export class GameDetailsLocalDataSource {

    static readonly CACHE_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    /**
     * Saves `GameDetails` of a single game to SQL database.
     */
    static async saveGameDetails(gameDetails: GameDetails) {
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
     * Returns `GameDetails` of a single game according to the `gameId` of that game.
     * Pay attention that `gameId` is the reference parameter of the table which contains game details.
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
     * Warn : This function is heavy. Use with caution.
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
}
