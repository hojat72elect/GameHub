import {Game} from "@/src/shared/domain/Game";
import {AccessTokenDatasource} from "@/src/shared/data/remote/AccessTokenDatasource";

export class SearchGamesDatasource {
    static async get(searchQuery: string): Promise<Game[]> {

        const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
        const token = await AccessTokenDatasource.get();

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
