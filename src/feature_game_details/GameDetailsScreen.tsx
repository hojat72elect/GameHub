import {SafeAreaProvider} from "react-native-safe-area-context";
import {ActivityIndicator, Image, Linking, ScrollView, Text, TouchableOpacity, View} from "react-native";
import {router, useLocalSearchParams} from "expo-router";
import {useEffect, useState} from "react";
import {getGameDetailsByIdUseCase} from "@/src/feature_game_details/api/getGameDetailsByIdUseCase";
import {GameDetails} from "@/src/feature_game_details/domain/GameDetails";
import idleImage from "@/assets/images/game_portrait_placeholder.webp";
import {useTheme} from "@/src/ThemeContext";
import {LikeButton} from "@/src/feature_game_details/LikeButton";
import WebIcon from "@/assets/svg/web.svg";
import WikipediaIcon from "@/assets/svg/wikipedia.svg";
import TwitterIcon from "@/assets/svg/twitter.svg";
import FacebookIcon from "@/assets/svg/facebook.svg";
import TwitchIcon from "@/assets/svg/twitch.svg";
import InstagramIcon from "@/assets/svg/instagram.svg";
import YoutubeIcon from "@/assets/svg/youtube.svg";
import AppleIcon from "@/assets/svg/apple.svg";
import GooglePlayIcon from "@/assets/svg/google_play.svg";
import SteamIcon from "@/assets/svg/steam.svg";
import RedditIcon from "@/assets/svg/reddit.svg";
import GogIcon from "@/assets/svg/gog.svg";
import DiscordIcon from "@/assets/svg/discord.svg";

export function GameDetailsScreen() {
    const {gameId} = useLocalSearchParams<{ gameId: string }>();
    const [gameDetails, setGameDetails] = useState<GameDetails | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const {colors} = useTheme();

    useEffect(() => {
        const loadGameDetails = async () => {
            setIsLoading(true);
            setError(null);
            try {
                const details = await getGameDetailsByIdUseCase(gameId);
                setGameDetails(details);
            } catch (err: any) {
                console.error("Error loading game details:", err);
                setError(err.message || "Failed to load game details");
            } finally {
                setIsLoading(false);
            }
        };

        loadGameDetails();
    }, [gameId]);

    const getCoverUrl = (imageId: string, size: "cover_big" | "1080p" = "cover_big") => {
        return `https://images.igdb.com/igdb/image/upload/t_${size}/${imageId}.jpg`;
    };

    const getYouTubeUrl = (videoId: string) => {
        return `https://www.youtube.com/watch?v=${videoId}`;
    };

    const getYouTubeThumbnailUrl = (videoId: string) => {
        return `https://img.youtube.com/vi/${videoId}/mqdefault.jpg`;
    };

    const getWebsiteInfoFromUrl = (url: string) => {
        const lowerUrl = url.toLowerCase();

        if (lowerUrl.includes('wikipedia.org')) {
            return {icon: WikipediaIcon, name: 'Wikipedia'};
        }
        if (lowerUrl.includes('twitter.com')) {
            return {icon: TwitterIcon, name: 'X'};
        }
        if (lowerUrl.includes('facebook.com')) {
            return {icon: FacebookIcon, name: 'Facebook'};
        }
        if (lowerUrl.includes('twitch.tv')) {
            return {icon: TwitchIcon, name: 'Twitch'};
        }
        if (lowerUrl.includes('instagram.com')) {
            return {icon: InstagramIcon, name: 'Instagram'};
        }
        if (lowerUrl.includes('youtube.com') || lowerUrl.includes('youtu.be')) {
            return {icon: YoutubeIcon, name: 'YouTube'};
        }
        if (lowerUrl.includes('steam')) {
            return {icon: SteamIcon, name: 'Steam'};
        }
        if (lowerUrl.includes('reddit.com')) {
            return {icon: RedditIcon, name: 'Reddit'};
        }
        if (lowerUrl.includes('discord')) {
            return {icon: DiscordIcon, name: 'Discord'};
        }
        if (lowerUrl.includes('gog.com')) {
            return {icon: GogIcon, name: 'GOG'};
        }
        if (lowerUrl.includes('apple.com') || lowerUrl.includes('itunes')) {
            return {icon: AppleIcon, name: 'Apple'};
        }
        if (lowerUrl.includes('play.google.com')) {
            return {icon: GooglePlayIcon, name: 'Google Play'};
        }
        if (lowerUrl.includes("epicgames.com")) {
            return {icon: WebIcon, name: "Epic Games"}
        }

        return {icon: WebIcon, name: 'Website'};
    };

    const getDeveloperName = () => {
        if (!gameDetails?.involved_companies) return "Unknown";
        const developer = gameDetails.involved_companies.find(ic => ic.developer);
        return developer?.company.name || "Unknown";
    };

    const formatDate = (timestamp?: number) => {
        if (!timestamp) return "TBD";
        return new Date(timestamp * 1000).toLocaleDateString("en-US", {
            year: "numeric",
            month: "long",
            day: "numeric"
        });
    };

    if (isLoading) {
        return (
            <SafeAreaProvider
                style={{flex: 1, backgroundColor: colors.background, justifyContent: "center", alignItems: "center"}}>
                <ActivityIndicator size="large" color="#FF4B7D"/>
            </SafeAreaProvider>
        );
    }

    if (error || !gameDetails) {
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
                }}>
                    Something went wrong
                </Text>
                <Text style={{
                    fontSize: 14,
                    color: colors.secondaryText,
                    marginBottom: 20,
                    textAlign: "center"
                }}>{error}</Text>
                <TouchableOpacity onPress={() => router.back()} style={{
                    backgroundColor: "#FF4B7D",
                    paddingHorizontal: 25,
                    paddingVertical: 12,
                    borderRadius: 8
                }}>
                    <Text style={{color: "#FFF", fontWeight: "600"}}>Go Back</Text>
                </TouchableOpacity>
            </SafeAreaProvider>
        );
    }

    const coverUrl = gameDetails.cover?.image_id
        ? {uri: getCoverUrl(gameDetails.cover.image_id, "1080p")}
        : idleImage;

    const titlePictureUrl = gameDetails.artworks && gameDetails.artworks.length > 0
        ? {uri: getCoverUrl(gameDetails.artworks[0].image_id, "cover_big")}
        : coverUrl;

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: colors.background}}>
            <ScrollView showsVerticalScrollIndicator={false}>

                <View>
                    <Image source={coverUrl} resizeMode="cover" style={{width: "100%", height: 300}}/>
                    <View style={{position: "absolute", bottom: 0, right: 20, marginBottom: -20}}>
                        <LikeButton gameId={gameId}/>
                    </View>
                </View>
                <View style={{padding: 20}}>
                    <View style={{flexDirection: "row", marginBottom: 15}}>
                        <Image
                            source={titlePictureUrl}
                            resizeMode="cover"
                            style={{width: 100, height: 150, borderRadius: 8, marginRight: 15}}
                        />
                        <View style={{flex: 1, justifyContent: "center"}}>
                            <Text style={{fontSize: 24, fontWeight: "bold", color: colors.text, marginBottom: 8}}>
                                {gameDetails.name}
                            </Text>
                            <Text style={{fontSize: 14, color: colors.secondaryText, marginBottom: 4}}>
                                Release Date: {formatDate(gameDetails.first_release_date)}
                            </Text>
                            <Text style={{fontSize: 14, color: colors.secondaryText}}>
                                Developer: {getDeveloperName()}
                            </Text>
                        </View>
                    </View>


                    {gameDetails.videos && gameDetails.videos.length > 0 && (
                        <View style={{marginBottom: 20}}>
                            <Text style={{fontSize: 18, fontWeight: "semibold", color: colors.text, marginBottom: 10}}>
                                Videos
                            </Text>
                            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
                                {gameDetails.videos.map((video) => (
                                    <TouchableOpacity
                                        key={video.id}
                                        style={{marginRight: 10}}
                                        onPress={() => {
                                            Linking.openURL(getYouTubeUrl(video.video_id));
                                        }}
                                    >
                                        <View>
                                            <Image
                                                source={{uri: getYouTubeThumbnailUrl(video.video_id)}}
                                                resizeMode="cover"
                                                style={{width: 200, height: 120, borderRadius: 6}}
                                            />
                                            <Text style={{
                                                fontSize: 13,
                                                color: colors.secondaryText,
                                                marginTop: 4,
                                                width: 200
                                            }}
                                                  numberOfLines={1}>
                                                {video.name || "Watch Video"}
                                            </Text>
                                        </View>
                                    </TouchableOpacity>
                                ))}
                            </ScrollView>
                        </View>
                    )}

                    {gameDetails.screenshots && gameDetails.screenshots.length > 0 && (
                        <View style={{marginBottom: 20}}>
                            <Text style={{fontSize: 18, fontWeight: "semibold", color: colors.text, marginBottom: 10}}>
                                Screenshots
                            </Text>
                            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
                                {gameDetails.screenshots.map((screenshot) => (
                                    <Image
                                        key={screenshot.id}
                                        source={{uri: getCoverUrl(screenshot.image_id, "1080p")}}
                                        resizeMode="cover"
                                        style={{width: 200, height: 120, borderRadius: 8, marginRight: 10}}
                                    />
                                ))}
                            </ScrollView>
                        </View>
                    )}

                    {gameDetails.websites && gameDetails.websites.length > 0 && (
                        <View style={{marginBottom: 20}}>
                            <Text style={{fontSize: 18, fontWeight: "bold", color: colors.text, marginBottom: 10}}>
                                Links
                            </Text>
                            <View style={{flexDirection: "row", flexWrap: "wrap", gap: 8}}>
                                {gameDetails.websites.map((website) => {
                                    const {icon: IconComponent, name} = getWebsiteInfoFromUrl(website.url);
                                    return (
                                        <TouchableOpacity
                                            key={website.id}
                                            style={{
                                                backgroundColor: colors.card,
                                                paddingHorizontal: 12,
                                                paddingVertical: 8,
                                                borderRadius: 20,
                                                flexDirection: "row",
                                                alignItems: "center",
                                                gap: 6,
                                                elevation: 3,
                                            }}
                                            onPress={() => {
                                                Linking.openURL(website.url);
                                            }}
                                        >
                                            <IconComponent width={16} height={16} fill={colors.secondaryText}/>
                                            <Text style={{color: colors.secondaryText, fontSize: 14}}>
                                                {name}
                                            </Text>
                                        </TouchableOpacity>
                                    );
                                })}
                            </View>
                        </View>
                    )}

                    {gameDetails.similar_games && gameDetails.similar_games.length > 0 && (
                        <View style={{marginBottom: 20}}>
                            <Text style={{fontSize: 18, fontWeight: "bold", color: colors.text, marginBottom: 10}}>
                                Similar Games
                            </Text>
                            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
                                {gameDetails.similar_games.map((similarGame) => {
                                    const similarCoverUrl = similarGame.cover?.image_id
                                        ? {uri: getCoverUrl(similarGame.cover.image_id)}
                                        : idleImage;
                                    return (
                                        <TouchableOpacity
                                            key={similarGame.id}
                                            style={{marginRight: 10}}
                                            onPress={() => router.push({
                                                pathname: '/game-details',
                                                params: {gameId: similarGame.id}
                                            })}
                                        >
                                            <Image
                                                source={similarCoverUrl}
                                                resizeMode="cover"
                                                style={{width: 100, height: 150, borderRadius: 8}}
                                            />
                                            <Text style={{fontSize: 12, color: colors.text, marginTop: 4, width: 100}}
                                                  numberOfLines={2}>
                                                {similarGame.name}
                                            </Text>
                                        </TouchableOpacity>
                                    );
                                })}
                            </ScrollView>
                        </View>
                    )}
                </View>
            </ScrollView>
        </SafeAreaProvider>
    );
}