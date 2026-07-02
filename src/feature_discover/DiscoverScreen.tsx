import {ActivityIndicator, RefreshControl, ScrollView, Text, TouchableOpacity, View} from "react-native";
import {SafeAreaProvider} from 'react-native-safe-area-context';
import SearchIcon from "@/assets/svg/magnify.svg";
import {useEffect, useState} from "react";
import {getGamesUseCase} from "./api/getGamesUseCase";
import {Game} from "@/src/feature_discover/domain/Game";
import {router} from "expo-router";
import {GamesCategory} from "@/src/feature_discover/domain/GamesCategory";
import {DiscoverScreenSection} from "@/src/feature_discover/DiscoverScreenSection";

export function DiscoverScreen() {
    const [popular, setPopular] = useState<Game[]>([]);
    const [recent, setRecent] = useState<Game[]>([]);
    const [soon, setSoon] = useState<Game[]>([]);
    const [anticipated, setAnticipated] = useState<Game[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [refreshing, setRefreshing] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const loadData = async (showRefreshIndicator = false) => {
        if (showRefreshIndicator) {
            setRefreshing(true);
        } else {
            setIsLoading(true);
        }
        setError(null);
        try {
            const data = await getGamesUseCase();
            setPopular(data.popularGames);
            setRecent(data.recentlyReleasedGames);
            setSoon(data.comingSoonGames);
            setAnticipated(data.mostAnticipatedGames);
        } catch (err: any) {
            console.error("Error loading IGDB discover data:", err);
            setError(err.message || "Failed to load discover data");
        } finally {
            setIsLoading(false);
            setRefreshing(false);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    if (isLoading && !refreshing) {
        return (
            <SafeAreaProvider
                style={{flex: 1, backgroundColor: "#FFF", justifyContent: "center", alignItems: "center"}}>
                <ActivityIndicator size="large" color="#FF4B7D"/>
            </SafeAreaProvider>
        );
    }

    if (error && !refreshing) {
        return (
            <SafeAreaProvider style={{
                flex: 1,
                backgroundColor: "#FFF",
                justifyContent: "center",
                alignItems: "center",
                paddingHorizontal: 20
            }}>
                <Text style={{fontSize: 20, fontWeight: "bold", color: "#333", marginBottom: 10, textAlign: "center"}}>Something
                    went wrong</Text>
                <Text style={{fontSize: 14, color: "#666", marginBottom: 20, textAlign: "center"}}>{error}</Text>
                <TouchableOpacity onPress={() => loadData()} style={{
                    backgroundColor: "#FF4B7D",
                    paddingHorizontal: 25,
                    paddingVertical: 12,
                    borderRadius: 8
                }}>
                    <Text style={{color: "#FFF", fontWeight: "600"}}>Retry</Text>
                </TouchableOpacity>
            </SafeAreaProvider>
        );
    }

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: "#FFF"}}>
            <View style={{
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
                paddingHorizontal: 20,
                paddingVertical: 15,
                borderBottomWidth: 1,
                borderBottomColor: "#F0F0F0"
            }}>
                <Text style={{fontSize: 28, fontWeight: "bold"}}>Discover</Text>
                <TouchableOpacity onPress={() => router.push('/search')}>
                    <SearchIcon width={24} height={24} color="black"/>
                </TouchableOpacity>
            </View>

            <ScrollView
                showsVerticalScrollIndicator={false}
                refreshControl={
                    <RefreshControl refreshing={refreshing} onRefresh={() => loadData(true)} colors={["#FF4B7D"]}
                                    tintColor="#FF4B7D"/>
                }
            >
                <DiscoverScreenSection title={GamesCategory.Popular} data={popular}/>
                <DiscoverScreenSection title={GamesCategory.RecentlyReleased} data={recent}/>
                <DiscoverScreenSection title={GamesCategory.ComingSoon} data={soon}/>
                <DiscoverScreenSection title={GamesCategory.MostAnticipated} data={anticipated}/>
            </ScrollView>
        </SafeAreaProvider>
    );
}
