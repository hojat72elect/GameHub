import {Game} from "@/src/shared/domain/Game";
import {getDatabase} from "@/src/shared/data/Database";

export class GamesGeneralLocalDataSource {
    /**
     * Cache duration in milliseconds (24 hours)
     */
    private static readonly CACHE_DURATION = 24 * 60 * 60 * 1000;

    /**
     * Save games to the local database
     */
    static async saveGamesByCategory(games: Game[], category: string): Promise<void> {
        const db = await getDatabase();
        const cachedAt = Date.now();

        for (const game of games) {
            await db.runAsync(
                `INSERT
                OR REPLACE INTO games_general 
                (id, name, cover_id, cover_image_id, first_release_date, hypes, follows, cached_at, category)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)`,
                [
                    game.id,
                    game.name,
                    game.cover?.id || null,
                    game.cover?.image_id || null,
                    game.first_release_date || null,
                    game.hypes || null,
                    (game as any).follows || null,
                    cachedAt,
                    category
                ]
            );
        }
    }

    /**
     * Get games by category from local database
     */
    static async getGamesByCategory(category: string): Promise<Game[] | null> {
        const db = await getDatabase();
        const now = Date.now();
        const cacheThreshold = now - this.CACHE_DURATION;

        const rows = await db.getAllAsync<any>(
            `SELECT *
             FROM games_general
             WHERE category = ?
               AND cached_at > ?
             ORDER BY CASE
                          WHEN category = 'popular' THEN follows
                          WHEN category = 'recent' THEN first_release_date
                          WHEN category = 'soon' THEN first_release_date
                          WHEN category = 'anticipated' THEN hypes
                          END DESC`,
            [category, cacheThreshold]
        );

        if (rows.length === 0) {
            return null;
        }

        return rows.map(row => ({
            id: row.id,
            name: row.name,
            cover: row.cover_id ? {
                id: row.cover_id,
                image_id: row.cover_image_id
            } : undefined,
            first_release_date: row.first_release_date,
            hypes: row.hypes,
            follows: row.follows
        } as Game));
    }

    /**
     * Clear old cache entries
     *
     * todo : Move this logic to non-UI thread which is fired up by a timer.
     */
    static async clearOldCache(): Promise<void> {
        const db = await getDatabase();
        const now = Date.now();
        const cacheThreshold = now - this.CACHE_DURATION;

        await db.runAsync(
            `DELETE
             FROM games_general
             WHERE cached_at <= ?`,
            [cacheThreshold]
        );
    }
}
