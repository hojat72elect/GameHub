import {NewsApiImage} from "@/src/feature_news/domain/NewsApiImage";

export type NewsApiResultItem = {
    publish_date: string;
    id: number;
    title: string;
    image: NewsApiImage;
    deck: string;
    site_detail_url: string;
};