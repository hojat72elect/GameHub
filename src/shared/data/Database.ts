import {openDatabaseAsync, SQLiteDatabase} from "expo-sqlite";

const DB_NAME = 'GameHub.db';
let dbInstance: SQLiteDatabase | null = null;

export async function getDatabase(): Promise<SQLiteDatabase> {
    if (dbInstance) {
        return dbInstance;
    }

    dbInstance = await openDatabaseAsync(DB_NAME);
    await initializeTables(dbInstance);
    return dbInstance;
}

async function initializeTables(db: SQLiteDatabase): Promise<void> {
    // Games general table - stores basic game info
    await db.execAsync(`
        CREATE TABLE IF NOT EXISTS games_general
        (
            id
            INTEGER
            PRIMARY
            KEY,
            name
            TEXT
            NOT
            NULL,
            cover_id
            INTEGER,
            cover_image_id
            TEXT,
            first_release_date
            INTEGER,
            hypes
            INTEGER,
            follows
            INTEGER,
            cached_at
            INTEGER
            NOT
            NULL,
            category
            TEXT
        );
    `);

    // Games detailed table - stores detailed game info
    await db.execAsync(`
        CREATE TABLE IF NOT EXISTS games_detailed
        (
            id
            INTEGER
            PRIMARY
            KEY,
            name
            TEXT
            NOT
            NULL,
            cover_id
            INTEGER,
            cover_image_id
            TEXT,
            artworks
            TEXT,
            first_release_date
            INTEGER,
            involved_companies
            TEXT,
            videos
            TEXT,
            screenshots
            TEXT,
            websites
            TEXT,
            similar_games
            TEXT,
            summary
            TEXT,
            genres
            TEXT,
            platforms
            TEXT,
            game_modes
            TEXT,
            player_perspectives
            TEXT,
            themes
            TEXT,
            cached_at
            INTEGER
            NOT
            NULL
        );
    `);

    // News table - stores news articles
    await db.execAsync(`
        CREATE TABLE IF NOT EXISTS news
        (
            id
            INTEGER
            PRIMARY
            KEY,
            title
            TEXT
            NOT
            NULL,
            deck
            TEXT,
            publish_date
            TEXT,
            site_detail_url
            TEXT,
            image_square_tiny
            TEXT,
            image_screen_tiny
            TEXT,
            image_square_small
            TEXT,
            image_original
            TEXT,
            cached_at
            INTEGER
            NOT
            NULL
        );
    `);

    // Create indexes for better query performance
    await db.execAsync(`
        CREATE INDEX IF NOT EXISTS idx_games_general_category ON games_general(category);
        CREATE INDEX IF NOT EXISTS idx_games_general_cached_at ON games_general(cached_at);
        CREATE INDEX IF NOT EXISTS idx_games_detailed_cached_at ON games_detailed(cached_at);
        CREATE INDEX IF NOT EXISTS idx_news_cached_at ON news(cached_at);
    `);
}
