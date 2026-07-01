import {ActivityIndicator, Image, RefreshControl, ScrollView, Text, TouchableOpacity, View} from "react-native";
import {SafeAreaProvider} from "react-native-safe-area-context";
import {router, useLocalSearchParams} from "expo-router";
import {GameCategory} from "@/src/feature_discover/domain/GameCategory";
import {Game} from "@/src/feature_discover/domain/Game";
import {getGamesByCategoryUseCase} from "./api/getGamesByCategoryUseCase";
import React, {useEffect, useState} from "react";
import idleImage from "@/assets/images/game_portrait_placeholder.webp";

export function CategoryGamesScreen() {
    const {category} = useLocalSearchParams<{ category?: string }>();
    const [games, setGames] = useState<Game[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [isRefreshing, setIsRefreshing] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const chosenCategory = category ? parseInt(category, 10) as GameCategory : null;

    const getCategoryTitle = () => {
        switch (chosenCategory) {
            case GameCategory.Popular:
                return "Popular Games";
            case GameCategory.RecentlyReleased:
                return "Recently Released Games";
            case GameCategory.ComingSoon:
                return "Coming Soon Games";
            case GameCategory.MostAnticipated:
                return "Most Anticipated Games";
            default:
                return "";
        }
    };

    const loadData = async (showRefreshIndicator = false) => {
        if (showRefreshIndicator) {
            setIsRefreshing(true);
        } else {
            setIsLoading(true);
        }
        setError(null);
        try {
            const data = await getGamesByCategoryUseCase(chosenCategory!);
            setGames(data);
        } catch (err: any) {
            console.error("Error loading category games:", err);
            setError(err.message || "Failed to load games");
        } finally {
            setIsLoading(false);
            setIsRefreshing(false);
        }
    };

    useEffect(() => {
        if (chosenCategory) {
            loadData();
        }
    }, [chosenCategory]);

    if (isLoading && !isRefreshing) {
        return (
            <SafeAreaProvider
                style={{flex: 1, backgroundColor: "#FFF", justifyContent: "center", alignItems: "center"}}>
                <ActivityIndicator size="large" color="#FF4B7D"/>
            </SafeAreaProvider>
        );
    }

    if (error && !isRefreshing) {
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
                paddingHorizontal: 20,
                paddingVertical: 15,
                borderBottomWidth: 1,
                borderBottomColor: "#F0F0F0"
            }}>
                <Text style={{fontSize: 28, fontWeight: "bold"}}>{getCategoryTitle()}</Text>
            </View>

            <ScrollView
                showsVerticalScrollIndicator={false}
                contentContainerStyle={{padding: 15}}
                refreshControl={
                    <RefreshControl refreshing={isRefreshing} onRefresh={() => loadData(true)} colors={["#FF4B7D"]}
                                    tintColor="#FF4B7D"/>
                }
            >
                <View style={{
                    flexDirection: "row",
                    flexWrap: "wrap",
                    justifyContent: "space-between"
                }}>
                    {games.map((item) => {
                        const coverUrl = item.cover?.image_id
                            ? {uri: `https://images.igdb.com/igdb/image/upload/t_cover_big/${item.cover.image_id}.jpg`}
                            : idleImage;
                        return (
                            <TouchableOpacity
                                key={item.id}
                                style={{
                                    width: "48%",
                                    marginBottom: 15,
                                    borderRadius: 8,
                                    overflow: "hidden",
                                    backgroundColor: "#EEE"
                                }}
                                onPress={()=> router.push({pathname: '/game-details', params: {gameId: item.id}})}
                            >
                                <Image source={coverUrl} resizeMode="cover" style={{width: "100%", height: 200}}/>
                                <View style={{padding: 10}}>
                                    <Text style={{fontSize: 14, fontWeight: "600", color: "#333"}} numberOfLines={2}>
                                        {item.name}
                                    </Text>
                                </View>
                            </TouchableOpacity>
                        );
                    })}
                </View>

                {games.length === 0 && (
                    <View style={{flex: 1, justifyContent: "center", alignItems: "center", marginTop: 50}}>
                        <Text style={{fontSize: 16, color: "#666"}}>No games found</Text>
                    </View>
                )}
            </ScrollView>
        </SafeAreaProvider>
    );
}