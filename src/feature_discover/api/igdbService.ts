import {Game} from "@/src/feature_discover/domain/Game";
import {DiscoverScreenData} from "@/src/feature_discover/domain/DiscoverScreenData";

let accessToken: string | null = null;
let tokenExpiryTime: number = 0;

/**
 * Retrieves a valid Twitch OAuth2 access token for the IGDB API.
 * Uses cached token if it is still valid.
 */
async function getAccessToken(): Promise<string> {
    const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
    const clientSecret = process.env.EXPO_PUBLIC_IGDB_SECRET;

    if (!clientId || !clientSecret) {
        throw new Error(
            "IGDB credentials are missing. Please verify that EXPO_PUBLIC_IGDB_CLIENT_ID and EXPO_PUBLIC_IGDB_SECRET are defined in your .env file."
        );
    }

    const now = Date.now();
    // Re-use cached token if it expires in more than 60 seconds
    if (accessToken && tokenExpiryTime > now + 60000) {
        return accessToken;
    }

    const url = `https://id.twitch.tv/oauth2/token?client_id=${clientId}&client_secret=${clientSecret}&grant_type=client_credentials`;

    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Failed to retrieve access token: ${response.status} ${errorText}`);
    }

    const data = await response.json();
    accessToken = data.access_token;
    // expires_in is in seconds; convert to absolute time in milliseconds
    tokenExpiryTime = now + data.expires_in * 1000;

    return accessToken!;
}

/**
 * Fetches popular, recently released, coming soon, and most anticipated games
 * in a single batch request using the IGDB multi-query endpoint.
 */
export async function fetchDiscoverData(): Promise<DiscoverScreenData> {
    const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
    const token = await getAccessToken();

    const currentTimestamp = Math.floor(Date.now() / 1000);

    // Build the Apicalypse multi-query body
    const body = `
query games "popular" {
    fields name, cover.image_id, follows;
    where cover != null & game_type = 0;
    sort follows desc;
    limit 10;
};

query games "recent" {
    fields name, cover.image_id, first_release_date;
    where first_release_date <= ${currentTimestamp} & cover != null & game_type = 0;
    sort first_release_date desc;
    limit 10;
};

query games "soon" {
    fields name, cover.image_id, first_release_date;
    where first_release_date > ${currentTimestamp} & cover != null & game_type = 0;
    sort first_release_date asc;
    limit 10;
};

query games "anticipated" {
    fields name, cover.image_id, hypes, first_release_date;
    where hypes != null & first_release_date > ${currentTimestamp} & cover != null & game_type = 0;
    sort hypes desc;
    limit 10;
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
