import {NewsArticleImage} from "@/src/shared/domain/NewsArticleImage";

export type NewsArticle = {
    publish_date: string;
    id: number;
    title: string;
    image: NewsArticleImage;
    deck: string;
    site_detail_url: string;
};