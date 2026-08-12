import {NewsArticle} from "@/src/shared/domain/NewsArticle";

export class NewsRemoteDataSource {
    static GAMESPOT_FEED_URL = "https://www.gamespot.com/feeds/news/";

    private static decodeHtmlEntities(str: string): string {
        return str
            .replace(/&amp;/g, "&")
            .replace(/&lt;/g, "<")
            .replace(/&gt;/g, ">")
            .replace(/&quot;/g, '"')
            .replace(/&#39;/g, "'")
            .replace(/&#8217;/g, "'")
            .replace(/&#8216;/g, "'")
            .replace(/&#8220;/g, '"')
            .replace(/&#8221;/g, '"')
            .replace(/&#8211;/g, "–")
            .replace(/&#8212;/g, "—")
            .replace(/&#160;/g, " ");
    }

    static async getRemoteArticlesUseCase(): Promise<NewsArticle[]> {
        const response = await fetch(NewsRemoteDataSource.GAMESPOT_FEED_URL);
        if (!response.ok) throw new Error(`Failed to fetch news: ${response.status}`);

        const xmlText = await response.text();

        return NewsRemoteDataSource.convertXmlToNewsArticles(xmlText)
    }

    static convertXmlToNewsArticles(xmlText: string): NewsArticle[] {
        const items: NewsArticle[] = [];
        const itemRegex = /<item>([\s\S]*?)<\/item>/g;
        let match;
        let index = 0;

        while ((match = itemRegex.exec(xmlText)) !== null) {
            const itemXml = match[1];

            // Extract title
            const titleMatch = itemXml.match(/<title>([\s\S]*?)<\/title>/);
            let title = titleMatch ? titleMatch[1].trim() : "";
            title = NewsRemoteDataSource.decodeHtmlEntities(title);

            // Extract link
            const linkMatch = itemXml.match(/<link>([\s\S]*?)<\/link>/);
            const link = linkMatch ? linkMatch[1].trim() : "";

            // Extract pubDate
            const pubDateMatch = itemXml.match(/<pubDate>([\s\S]*?)<\/pubDate>/);
            const pubDate = pubDateMatch ? pubDateMatch[1].trim() : "";

            // Extract description (first paragraph) for deck
            const descMatch = itemXml.match(/<description>([\s\S]*?)<\/description>/);
            let deck = "";
            if (descMatch) {
                const description = descMatch[1];
                const pMatch = description.match(/<p[^>]*>([\s\S]*?)<\/p>/);
                let rawDeck = pMatch ? pMatch[1] : description;
                rawDeck = rawDeck.replace(/<[^>]+>/g, ""); // Strip HTML tags
                rawDeck = rawDeck.replace(/<!\[CDATA\[|]]>/g, ""); // Clean CDATA markers
                deck = NewsRemoteDataSource.decodeHtmlEntities(rawDeck.trim());
            }

            // Extract media:content URL
            const mediaMatch = itemXml.match(/<media:content[^>]+url=["']([^"']+)["']/);
            let imageUrl = "";
            if (mediaMatch) {
                imageUrl = mediaMatch[1].trim();
            }

            // Strip ?w= query parameters from image URL to get high-res original
            const originalImageUrl = imageUrl.split("?")[0];

            items.push({
                id: index++,
                title,
                deck,
                publish_date: pubDate,
                site_detail_url: link,
                image: {
                    square_tiny: imageUrl,
                    screen_tiny: imageUrl,
                    square_small: imageUrl,
                    original: originalImageUrl,
                }
            });
        }

        return items;
    }
}