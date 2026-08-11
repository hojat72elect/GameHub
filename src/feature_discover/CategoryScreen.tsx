import {ActivityIndicator, Image, RefreshControl, ScrollView, Text, TouchableOpacity, View} from "react-native";
import {SafeAreaProvider} from "react-native-safe-area-context";
import {router, useLocalSearchParams} from "expo-router";
import {GamesCategory} from "@/src/shared/domain/GamesCategory";
import {Game} from "@/src/shared/domain/Game";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import idleImage from "@/assets/images/game_portrait_placeholder.webp";
import {useTheme} from "@/src/ThemeContext";
import {GamesGeneralRepository} from "@/src/shared/data/GamesGeneralRepository";

export function CategoryScreen() {
    const {category} = useLocalSearchParams<{ category?: string }>();
    const [games, setGames] = useState<Game[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [isRefreshing, setIsRefreshing] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const {colors} = useTheme();
    const {t} = useTranslation();

    const chosenCategory = category ? parseInt(category, 10) as GamesCategory : null;

    const getCategoryTitle = () => {
        switch (chosenCategory) {
            case GamesCategory.Popular:
                return t('popular');
            case GamesCategory.RecentlyReleased:
                return t('recentlyReleased');
            case GamesCategory.ComingSoon:
                return t('comingSoon');
            case GamesCategory.MostAnticipated:
                return t('mostAnticipated');
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
            const data = await GamesGeneralRepository.getCategoryScreenGames(chosenCategory!);
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
                style={{flex: 1, backgroundColor: colors.background, justifyContent: "center", alignItems: "center"}}>
                <ActivityIndicator size="large" color="#FF4B7D"/>
            </SafeAreaProvider>
        );
    }

    if (error && !isRefreshing) {
        return (
            <SafeAreaProvider style={{
                flex: 1,
                backgroundColor: colors.background,
                justifyContent: "center",
                alignItems: "center",
                paddingHorizontal: 20
            }}>
                <Text style={{
                    fontSize: 20,
                    fontWeight: "bold",
                    color: colors.text,
                    marginBottom: 10,
                    textAlign: "center"
                }}>{t('somethingWentWrong')}</Text>
                <Text style={{
                    fontSize: 14,
                    color: colors.secondaryText,
                    marginBottom: 20,
                    textAlign: "center"
                }}>{error}</Text>
                <TouchableOpacity onPress={() => loadData()} style={{
                    backgroundColor: "#FF4B7D",
                    paddingHorizontal: 25,
                    paddingVertical: 12,
                    borderRadius: 8
                }}>
                    <Text style={{color: "#FFF", fontWeight: "600"}}>{t('retry')}</Text>
                </TouchableOpacity>
            </SafeAreaProvider>
        );
    }

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: colors.background}}>
            <View style={{
                paddingHorizontal: 20,
                paddingVertical: 15,
                borderBottomWidth: 1,
                borderBottomColor: colors.border,
                flexDirection: "row",
                alignItems: "center"
            }}>
                <TouchableOpacity onPress={() => router.back()} style={{marginRight: 15}}>
                    <Text style={{fontSize: 18, color: "#FF4B7D", fontWeight: "600"}}>{t('back')}</Text>
                </TouchableOpacity>
                <Text style={{fontSize: 24, fontWeight: "bold", color: colors.text}}>{getCategoryTitle()}</Text>
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
                                    backgroundColor: colors.card,
                                    borderWidth: 1,
                                    borderColor: colors.border
                                }}
                                onPress={() => router.push({pathname: '/game-details', params: {gameId: item.id}})}
                            >
                                <Image source={coverUrl} resizeMode="cover" style={{width: "100%", height: 200}}/>
                                <View style={{padding: 10}}>
                                    <Text style={{fontSize: 14, fontWeight: "600", color: colors.text}}
                                          numberOfLines={2}>
                                        {item.name}
                                    </Text>
                                </View>
                            </TouchableOpacity>
                        );
                    })}
                </View>

                {games.length === 0 && (
                    <View style={{flex: 1, justifyContent: "center", alignItems: "center", marginTop: 50}}>
                        <Text style={{fontSize: 16, color: colors.secondaryText}}>{t('noGamesFound')}</Text>
                    </View>
                )}
            </ScrollView>
        </SafeAreaProvider>
    );
}