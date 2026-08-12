import {NewsArticle} from "@/src/shared/domain/NewsArticle";
import {NewsLocalDataSource} from "@/src/feature_news/data/NewsLocalDataSource";
import {NewsRemoteDataSource} from "@/src/feature_news/data/NewsRemoteDataSource";

export class NewsRepository {

    static async getNewsArticles(): Promise<NewsArticle[]> {
        // Try to get from local cache first
        const cachedNews = await NewsLocalDataSource.getNews();
        if (cachedNews) return cachedNews;

        // Otherwise, fetch from remote API and save them to local cache
        const remoteNews = await NewsRemoteDataSource.getRemoteArticlesUseCase();
        await NewsLocalDataSource.saveNews(remoteNews);

        // Clear old cache entries
        await NewsLocalDataSource.clearOldCache();

        return remoteNews;
    }
}
