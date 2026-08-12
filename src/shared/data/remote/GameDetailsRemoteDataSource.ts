import {GameDetails} from "@/src/shared/domain/GameDetails";
import {AccessTokenDatasource} from "@/src/shared/data/remote/AccessTokenDatasource";

export class GameDetailsRemoteDataSource {
    static async getGameDetailsById(gameId: string): Promise<GameDetails> {

        const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
        const token = await AccessTokenDatasource.get();

        // Build the Apicalypse query to fetch required fields
        const body = `
    fields name, cover.image_id, artworks.image_id, first_release_date, 
           involved_companies.company.name, involved_companies.developer, involved_companies.publisher,
           videos.name, videos.video_id, screenshots.image_id, 
           websites.category, websites.url,
           similar_games.name, similar_games.cover.image_id,
           summary, genres.name, platforms.name, game_modes.name, 
           player_perspectives.name, themes.name;
    where id = ${gameId};
    limit 1;
    `.trim();

        const response = await fetch("https://api.igdb.com/v4/games", {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Client-ID": clientId!,
                "Authorization": `Bearer ${token}`,
                "Content-Type": "text/plain",
            },
            body: body,
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`IGDB API error: ${response.status} ${errorText}`);
        }

        const data = await response.json();

        if (!Array.isArray(data) || data.length === 0) {
            throw new Error(`Game with ID ${gameId} not found`);
        }

        return data[0] as GameDetails;
    }

    /**
     * Get remote game info for multiple games.
     */
    static async getGamesDetailsByIds(gameIds: string[]): Promise<GameDetails[]> {

        if (gameIds.length === 0) return [];

        const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
        const token = await AccessTokenDatasource.get();

        const body = `
    fields name, cover.image_id, artworks.image_id, first_release_date, 
           involved_companies.company.name, involved_companies.developer, involved_companies.publisher,
           videos.name, videos.video_id, screenshots.image_id, 
           websites.category, websites.url,
           similar_games.name, similar_games.cover.image_id, summary;
    where id = (${gameIds.join(",")});
    limit ${gameIds.length};
    `.trim();

        const response = await fetch("https://api.igdb.com/v4/games", {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Client-ID": clientId!,
                "Authorization": `Bearer ${token}`,
                "Content-Type": "text/plain",
            },
            body: body,
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`IGDB API error: ${response.status} ${errorText}`);
        }

        const data = await response.json();

        if (!Array.isArray(data))
            throw new Error("Invalid response from IGDB API");

        return data as GameDetails[];
    }
}