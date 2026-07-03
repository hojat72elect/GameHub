import {Game} from "@/src/feature_discover/domain/Game";
import {DiscoverScreenData} from "@/src/feature_discover/domain/DiscoverScreenData";
import {AccessTokenDatasource} from "@/src/feature_discover/api/AccessTokenDatasource";

/**
 * Fetches popular, recently released, coming soon, and most anticipated games
 * in a single batch request using the IGDB multi-query endpoint.
 */
export async function getGamesUseCase(): Promise<DiscoverScreenData> {
    const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
    const token = await AccessTokenDatasource.get();

    const currentTimestamp = Math.floor(Date.now() / 1000);

    // Build the Apicalypse multi-query body
    const body = `
query games "popular" {
    fields name, cover.image_id, follows;
    where cover != null & game_type = 0;
    sort follows desc;
    limit 20;
};

query games "recent" {
    fields name, cover.image_id, first_release_date;
    where first_release_date <= ${currentTimestamp} & cover != null & game_type = 0;
    sort first_release_date desc;
    limit 20;
};

query games "soon" {
    fields name, cover.image_id, first_release_date;
    where first_release_date > ${currentTimestamp} & cover != null & game_type = 0;
    sort first_release_date asc;
    limit 20;
};

query games "anticipated" {
    fields name, cover.image_id, hypes, first_release_date;
    where hypes != null & first_release_date > ${currentTimestamp} & cover != null & game_type = 0;
    sort hypes desc;
    limit 20;
};
`.trim();

    const response = await fetch("https://api.igdb.com/v4/multiquery", {
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
        throw new Error(`IGDB Multi-Query API error: ${response.status} ${errorText}`);
    }

    const results = await response.json();

    const getResultByName = (name: string): Game[] => {
        const queryResult = results.find((r: any) => r.name === name);
        return queryResult && Array.isArray(queryResult.result) ? queryResult.result : [];
    };

    return {
        popularGames: getResultByName("popular"),
        recentlyReleasedGames: getResultByName("recent"),
        comingSoonGames: getResultByName("soon"),
        mostAnticipatedGames: getResultByName("anticipated"),
    };
}
