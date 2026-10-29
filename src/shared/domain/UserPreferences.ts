import type {Language} from "@/src/shared/domain/Language";
import type {ThemeMode} from "@/src/shared/domain/ThemeMode";

/**
 * The preferences of the user that follow the account instead of the device.
 * They are kept on the device, and mirrored into the account of the user when he is signed in.
 */
export interface UserPreferences {
    /** IDs of the games that the user has liked. */
    likedGameIds: string[];
    themeMode: ThemeMode;
    language: Language;
}
