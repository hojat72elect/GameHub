import {NewsApiImage} from "@/src/shared/domain/NewsApiImage";

export type NewsApiResultItem = {
    publish_date: string;
    id: number;
    title: string;
    image: NewsApiImage;
    deck: string;
    site_detail_url: string;
};