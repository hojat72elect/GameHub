import {Game} from "@/src/shared/domain/Game";
import {AccessTokenDatasource} from "@/src/shared/data/AccessTokenDatasource";

/**
 * At this point, we only can search for games, but maybe in the future, the search could be
 * customized to include game studios and game characters and so on as well.
 */
export class SearchDatasource {
    static async getSearchResults(searchQuery: string): Promise<Game[]> {

        const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
        const token = await AccessTokenDatasource.getToken();

        const query = `
            search "${searchQuery}";
            fields name, cover.image_id, first_release_date;
            where cover != null & game_type = 0;
            limit 30;
        `;

        const response = await fetch("https://api.igdb.com/v4/games", {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Client-ID": clientId!,
                "Authorization": `Bearer ${token}`,
                "Content-Type": "text/plain",
            },
            body: query.trim(),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`IGDB API error: ${response.status} ${errorText}`);
        }

        const games: Game[] = await response.json();
        return games || [];
    }
}
