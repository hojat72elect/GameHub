import {NewsArticle} from "@/src/shared/domain/NewsArticle";
import {getDatabase} from "@/src/shared/data/Database";

export class NewsLocalDataSource {
    /**
     * Cache duration in milliseconds (1 hour for news)
     */
    private static readonly CACHE_DURATION = 60 * 60 * 1000;

    /**
     * Save news articles to the local database
     */
    static async saveNews(articles: NewsArticle[]): Promise<void> {
        const db = await getDatabase();
        const cachedAt = Date.now();

        for (const article of articles) {
            await db.runAsync(
                `INSERT
                OR REPLACE INTO news 
                (id, title, deck, publish_date, site_detail_url, 
                 image_square_tiny, image_screen_tiny, image_square_small, image_original, cached_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`,
                [
                    article.id,
                    article.title,
                    article.deck || null,
                    article.publish_date,
                    article.site_detail_url,
                    article.image.square_tiny || null,
                    article.image.screen_tiny || null,
                    article.image.square_small || null,
                    article.image.original || null,
                    cachedAt
                ]
            );
        }
    }

    /**
     * Get all news articles from local database
     */
    static async getNews(): Promise<NewsArticle[] | null> {
        const db = await getDatabase();
        const now = Date.now();
        const cacheThreshold = now - this.CACHE_DURATION;

        const rows = await db.getAllAsync<any>(
            `SELECT *
             FROM news
             WHERE cached_at > ?
             ORDER BY publish_date DESC`,
            [cacheThreshold]
        );

        if (rows.length === 0) {
            return null;
        }

        return rows.map(row => ({
            id: row.id,
            title: row.title,
            deck: row.deck,
            publish_date: row.publish_date,
            site_detail_url: row.site_detail_url,
            image: {
                square_tiny: row.image_square_tiny,
                screen_tiny: row.image_screen_tiny,
                square_small: row.image_square_small,
                original: row.image_original
            }
        } as NewsArticle));
    }

    /**
     * Clear old cache entries
     *
     * todo : move this off the UI thread.
     */
    static async clearOldCache(): Promise<void> {
        const db = await getDatabase();
        const now = Date.now();
        const cacheThreshold = now - this.CACHE_DURATION;

        await db.runAsync(
            `DELETE
             FROM news
             WHERE cached_at <= ?`,
            [cacheThreshold]
        );
    }
}
