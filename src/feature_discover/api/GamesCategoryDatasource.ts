import {GamesCategory} from "@/src/feature_discover/domain/GamesCategory";
import {Game} from "@/src/feature_discover/domain/Game";
import {AccessTokenDatasource} from "@/src/feature_discover/api/AccessTokenDatasource";

/**
 * This datasource is for getting the games of a specific category.
 * We show this data in the `GamesCategoryScreen`.
 */
export class GamesCategoryDatasource {
    /**
     * todo : I still need to add pagination to this datasource.
     */
    static async get(category: GamesCategory): Promise<Game[]> {

        const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
        const token = await AccessTokenDatasource.get();
        const currentTimestamp = Math.floor(Date.now() / 1_000);
        let query: string;

        switch (category) {
            case GamesCategory.Popular:
                query = `
                fields name, cover.image_id, follows;
                where cover != null & game_type = 0;
                sort follows desc;
                limit 50;
            `;
                break;
            case GamesCategory.RecentlyReleased:
                query = `
                fields name, cover.image_id, first_release_date;
                where first_release_date <= ${currentTimestamp} & cover != null & game_type = 0;
                sort first_release_date desc;
                limit 50;
            `;
                break;
            case GamesCategory.ComingSoon:
                query = `
                fields name, cover.image_id, first_release_date;
                where first_release_date > ${currentTimestamp} & cover != null & game_type = 0;
                sort first_release_date asc;
                limit 50;
            `;
                break;
            case GamesCategory.MostAnticipated:
                query = `
                fields name, cover.image_id, hypes, first_release_date;
                where hypes != null & first_release_date > ${currentTimestamp} & cover != null & game_type = 0;
                sort hypes desc;
                limit 50;
            `;
                break;
            default:
                throw new Error(`Unknown category: ${category}`);
        }

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