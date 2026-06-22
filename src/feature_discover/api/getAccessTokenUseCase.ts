let accessToken: string | null = null;
let tokenExpiryTime: number = 0;

/**
 * Retrieves a valid Twitch access token (OAuth2) which will be used for connecting to the IGDB API.
 * Uses the cached token if it is still valid.
 */
export async function getAccessTokenUseCase(): Promise<string> {
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
    tokenExpiryTime = now + data.expires_in * 1_000;

    return accessToken!;
}