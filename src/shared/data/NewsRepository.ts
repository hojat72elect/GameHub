import {NewsApiResultItem} from "@/src/shared/domain/NewsApiResultItem";
import {NewsLocalDataSource} from "@/src/shared/database/local_datasources/NewsLocalDataSource";
import {GAMESPOT_FEED_URL, getRemoteArticlesUseCase} from "@/src/feature_news/api/getRemoteArticlesUseCase";

export class NewsRepository {

    static async getNewsArticles(): Promise<NewsApiResultItem[]> {
        // Try to get from local cache first
        const cachedNews = await NewsLocalDataSource.getNews();
        if (cachedNews) return cachedNews;

        // Otherwise, fetch from remote API
        const response = await fetch(GAMESPOT_FEED_URL);
        if (!response.ok) throw new Error(`Failed to fetch news: ${response.status}`);

        const xmlText = await response.text();
        const remoteNews = getRemoteArticlesUseCase(xmlText);

        // Save to local cache
        await NewsLocalDataSource.saveNews(remoteNews);

        // Clear old cache entries
        await NewsLocalDataSource.clearOldCache();

        return remoteNews;
    }
}
