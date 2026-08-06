import {ActivityIndicator, FlatList, Image, Text, TouchableOpacity, View} from "react-native";
import {SafeAreaView} from "react-native-safe-area-context";
import React, {useEffect, useState} from "react";
import {useTheme} from "@/src/ThemeContext";
import {useLikes} from "@/src/feature_likes/LikesContext";
import {GameDetails} from "@/src/feature_game_details/domain/GameDetails";
import {router} from "expo-router";
import idleImage from "@/assets/images/game_portrait_placeholder.webp";
import {getGamesByIdsUseCase} from "@/src/feature_likes/getGamesByIdsUseCase";
import {getRelativeTimeTextUseCase} from "@/src/shared/getRelativeTimeTextUseCase";

export function LikesScreen() {
    const {colors} = useTheme();
    const {likedGameIds} = useLikes();
    const [likedGames, setLikedGames] = useState<GameDetails[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const loadLikedGames = async () => {
            setIsLoading(true);
            setError(null);

            if (likedGameIds.size === 0) {
                setLikedGames([]);
                setIsLoading(false);
                return;
            }

            try {
                const gameIds = Array.from(likedGameIds);
                const games = await getGamesByIdsUseCase(gameIds);
                setLikedGames(games);
            } catch (err: any) {
                console.error("Error loading liked games:", err);
                setError(err.message || "Failed to load liked games");
            } finally {
                setIsLoading(false);
            }
        };

        loadLikedGames();
    }, [likedGameIds]);

    const getCoverUrl = (imageId: string, size: "cover_big" | "1080p" = "cover_big") => {
        return `https://images.igdb.com/igdb/image/upload/t_${size}/${imageId}.jpg`;
    };

    const formatDate = (timestamp?: number) => {
        if (!timestamp) return "TBD";
        return new Date(timestamp * 1000).toLocaleDateString("en-US", {
            year: "numeric",
            month: "long",
            day: "numeric"
        });
    };

    const renderGameCard = ({item}: { item: GameDetails }) => {
        const coverUrl = item.cover?.image_id
            ? {uri: getCoverUrl(item.cover.image_id)}
            : idleImage;

        return (
            <TouchableOpacity
                style={{
                    flexDirection: "row",
                    backgroundColor: colors.card,
                    marginBottom: 8,
                    padding: 12,
                    elevation: 2,
                }}
                onPress={() => router.push({
                    pathname: '/game-details',
                    params: {gameId: item.id.toString()}
                })}
            >
                <Image
                    source={coverUrl}
                    resizeMode="cover"
                    style={{width: 120, height: 160, borderRadius: 8}}
                />
                <View style={{flex: 1, marginLeft: 12, justifyContent: "flex-start"}}>
                    <Text style={{fontSize: 18, fontWeight: "semibold", color: colors.text, marginBottom: 4}}
                          numberOfLines={2}>
                        {item.name}
                    </Text>
                    <Text style={{fontSize: 14, color: colors.secondaryText}}>{formatDate(item.first_release_date)} {item.first_release_date ? getRelativeTimeTextUseCase(item.first_release_date!) : ""}</Text>
                    <Text style={{
                        fontSize: 14,
                        color: colors.secondaryText
                    }}>{item.involved_companies ? item.involved_companies[0].company.name : ""}</Text>
                    <Text style={{color: colors.secondaryText, paddingTop: 8}}>Some information about the game</Text>
                </View>
            </TouchableOpacity>
        );
    };

    const renderEmptyState = () => (
        <View style={{flex: 1, justifyContent: "center", alignItems: "center", paddingHorizontal: 20}}>
            <Text style={{fontSize: 20, fontWeight: "bold", color: colors.text, marginBottom: 10}}>
                No liked games yet
            </Text>
            <Text style={{fontSize: 14, color: colors.secondaryText, textAlign: "center"}}>
                Start liking games to see them here!
            </Text>
        </View>
    );

    if (isLoading) {
        return (
            <SafeAreaView
                style={{flex: 1, backgroundColor: colors.background, justifyContent: "center", alignItems: "center"}}>
                <ActivityIndicator size="large" color="#FF4B7D"/>
            </SafeAreaView>
        );
    }

    if (error) {
        return (
            <SafeAreaView style={{
                flex: 1,
                backgroundColor: colors.background,
                justifyContent: "center",
                alignItems: "center",
                paddingHorizontal: 20
            }}>
                <Text style={{
                    fontSize: 18,
                    fontWeight: "bold",
                    color: colors.text,
                    marginBottom: 10,
                    textAlign: "center"
                }}>
                    Something went wrong
                </Text>
                <Text style={{fontSize: 14, color: colors.secondaryText, textAlign: "center"}}>
                    {error}
                </Text>
            </SafeAreaView>
        );
    }

    return (
        <SafeAreaView style={{flex: 1, backgroundColor: colors.background}}>
            <View style={{
                paddingHorizontal: 20,
                paddingVertical: 12,
                borderBottomWidth: 1,
                borderBottomColor: colors.border,
            }}>
                <Text style={{fontSize: 20, fontWeight: "500", color: colors.text}}>
                    Likes
                </Text>
            </View>
            <FlatList
                data={likedGames}
                renderItem={renderGameCard}
                keyExtractor={(item) => item.id.toString()}
                ListEmptyComponent={renderEmptyState}
            />
        </SafeAreaView>
    );
}