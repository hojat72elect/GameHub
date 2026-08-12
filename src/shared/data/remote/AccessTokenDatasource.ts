/**
 * This datasource gives us a Twitch access token.
 * we need that token for making API calls to IGDB API.
 */
export class AccessTokenDatasource {

    private static accessToken: string | null = null;
    private static tokenExpiryTime = 0;

    static async get(): Promise<string> {
        const clientId = process.env.EXPO_PUBLIC_IGDB_CLIENT_ID;
        const clientSecret = process.env.EXPO_PUBLIC_IGDB_SECRET;

        if (!clientId || !clientSecret) {
            throw new Error("IGDB credentials are missing. Please verify that EXPO_PUBLIC_IGDB_CLIENT_ID and EXPO_PUBLIC_IGDB_SECRET are defined in your .env file.");
        }

        const now = Date.now();
        // if the current (cached) access token expires in more than 60 seconds, we can use it
        if (this.accessToken && this.tokenExpiryTime > now + 60_000)
            return this.accessToken;

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
        this.accessToken = data.access_token;
        this.tokenExpiryTime = now + data.expires_in * 1_000; // expires_in is in seconds; convert to absolute time in milliseconds

        return this.accessToken!;
    }
}