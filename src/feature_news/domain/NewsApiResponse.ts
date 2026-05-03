import {NewsApiResultItem} from "@/src/feature_news/domain/NewsApiResultItem";

export type NewsApiResponse = {
    error: string;
    limit: number;
    offset: number;
    number_of_page_results: number;
    number_of_total_results: number;
    status_code: number;
    results: NewsApiResultItem[];
    version: string;
};