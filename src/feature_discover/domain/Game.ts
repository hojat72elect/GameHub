export interface Game {
    id: number;
    name: string;
    cover?: {
        id: number;
        image_id: string;
    };
    first_release_date?: number;
    follows?: number;
    hypes?: number;
}