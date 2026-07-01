import {Game} from "@/src/feature_discover/domain/Game";
import {GameCategory} from "@/src/feature_discover/domain/GameCategory";
import {getAccessTokenUseCase} from "@/src/feature_discover/api/getAccessTokenUseCase";

/**
 * Fetches games for a specific category with a larger limit for the "See All" screen.
 *
 * TODO : I still need to add pagination to this UseCase.
 */
export async function getGamesByCategoryUseCase(category: GameCategory): Promise<Game[]> {
    const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
    const token = await getAccessTokenUseCase();

    const currentTimestamp = Math.floor(Date.now() / 1000);

    let query: string;

    switch (category) {
        case GameCategory.Popular:
            query = `
                fields name, cover.image_id, follows;
                where cover != null & game_type = 0;
                sort follows desc;
                limit 50;
            `;
            break;
        case GameCategory.RecentlyReleased:
            query = `
                fields name, cover.image_id, first_release_date;
                where first_release_date <= ${currentTimestamp} & cover != null & game_type = 0;
                sort first_release_date desc;
                limit 50;
            `;
            break;
        case GameCategory.ComingSoon:
            query = `
                fields name, cover.image_id, first_release_date;
                where first_release_date > ${currentTimestamp} & cover != null & game_type = 0;
                sort first_release_date asc;
                limit 50;
            `;
            break;
        case GameCategory.MostAnticipated:
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
