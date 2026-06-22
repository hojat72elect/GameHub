import {Game} from "@/src/feature_discover/domain/Game";

export interface DiscoverScreenData {
    popularGames: Game[];
    recentlyReleasedGames: Game[];
    comingSoonGames: Game[];
    mostAnticipatedGames: Game[];
}