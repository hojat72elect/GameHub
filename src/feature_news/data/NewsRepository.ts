import {NewsArticle} from "@/src/shared/domain/NewsArticle";
import {NewsLocalDataSource} from "@/src/feature_news/data/NewsLocalDataSource";
import {NewsRemoteDataSource} from "@/src/feature_news/data/NewsRemoteDataSource";

/**
 * The only repository that is used for our `news` feature.
 */
export class NewsRepository {

    static async getNewsArticles(): Promise<NewsArticle[]> {
        // Try to get te news from local database (cache)
        const cachedNews = await NewsLocalDataSource.getNews();
        if (cachedNews) return cachedNews;

        // Otherwise, fetch news from remote API (and save them to local cache as well).
        const remoteNews = await NewsRemoteDataSource.getNews();
        await NewsLocalDataSource.saveNews(remoteNews);

        // Clear old cache entries
        await NewsLocalDataSource.clearOldCache();

        return remoteNews;
    }
}
