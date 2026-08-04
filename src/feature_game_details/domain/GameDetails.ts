export interface GameDetails {
    id: number;
    name: string;
    cover?: {
        id: number;
        image_id: string;
    };
    artworks?: Array<{
        id: number;
        image_id: string;
    }>;
    first_release_date?: number;
    involved_companies?: Array<{
        company: {
            id: number;
            name: string;
        };
        developer: boolean;
        publisher: boolean;
    }>;
    videos?: Array<{
        id: number;
        name: string;
        video_id: string;
    }>;
    screenshots?: Array<{
        id: number;
        image_id: string;
    }>;
    websites?: Array<{
        id: number;
        category: number;
        url: string;
    }>;
    similar_games?: Array<{
        id: number;
        name: string;
        cover?: {
            id: number;
            image_id: string;
        };
    }>;
    summary?: string;
    genres?: Array<{
        id: number;
        name: string;
    }>;
    platforms?: Array<{
        id: number;
        name: string;
    }>;
    game_modes?: Array<{
        id: number;
        name: string;
    }>;
    player_perspectives?: Array<{
        id: number;
        name: string;
    }>;
    themes?: Array<{
        id: number;
        name: string;
    }>;
}
