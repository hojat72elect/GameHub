import {SafeAreaProvider, useSafeAreaInsets} from "react-native-safe-area-context";
import {
    ActivityIndicator,
    Image,
    LayoutAnimation,
    Linking,
    ScrollView,
    Text,
    TouchableOpacity,
    View
} from "react-native";
import {router, useLocalSearchParams} from "expo-router";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
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
import PlayIcon from "@/assets/svg/play.svg";
import {getRelativeTimeTextUseCase} from "@/src/shared/getRelativeTimeTextUseCase";
import GamepadIcon from '@/assets/svg/gamepad_variant_outline.svg';

export function GameDetailsScreen() {
    const {gameId} = useLocalSearchParams<{ gameId: string }>();
    const [gameDetails, setGameDetails] = useState<GameDetails | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [isSummaryExpanded, setIsSummaryExpanded] = useState<boolean>(false);
    const {colors} = useTheme();
    const {t} = useTranslation();
    const {bottom} = useSafeAreaInsets();

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

    const toggleSummary = () => {
        LayoutAnimation.configureNext({
            duration: 500,
            create: {
                type: LayoutAnimation.Types.easeInEaseOut,
                property: LayoutAnimation.Properties.opacity,
            },
            update: {
                type: LayoutAnimation.Types.easeInEaseOut,
            },
        });
        setIsSummaryExpanded(!isSummaryExpanded);
    };

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

        if (lowerUrl.includes('wikipedia.org')) return {icon: WikipediaIcon, name: t('wikipedia')};
        if (lowerUrl.includes("store.playstation.com")) return {icon: WebIcon, name: t('PlayStation')}
        if (lowerUrl.includes("xbox.com")) return {icon: WebIcon, name: t('Xbox')}
        if (lowerUrl.includes("fandom.com")) return {icon: WebIcon, name: t('Fandom')}
        if (lowerUrl.includes('twitter.com') || lowerUrl.includes("//x.com")) return {icon: TwitterIcon, name: t('x')};
        if (lowerUrl.includes('nintendo.com')) return {icon: WebIcon, name: t("Nintendo")};
        if (lowerUrl.includes('facebook.com')) return {icon: FacebookIcon, name: t('facebook')};
        if (lowerUrl.includes('twitch.tv')) return {icon: TwitchIcon, name: t('twitch')};
        if (lowerUrl.includes('instagram.com')) return {icon: InstagramIcon, name: t('instagram')};
        if (lowerUrl.includes('youtube.com') || lowerUrl.includes('youtu.be')) return {icon: YoutubeIcon, name: t('youtube')};
        if (lowerUrl.includes('steam')) return {icon: SteamIcon, name: t('steam')};
        if (lowerUrl.includes('reddit.com')) return {icon: RedditIcon, name: t('reddit')};
        if (lowerUrl.includes('discord')) return {icon: DiscordIcon, name: t('discord')};
        if (lowerUrl.includes('gog.com')) return {icon: GogIcon, name: t('gog')};
        if (lowerUrl.includes('apple.com') || lowerUrl.includes('itunes')) return {icon: AppleIcon, name: t('apple')};
        if (lowerUrl.includes('play.google.com')) return {icon: GooglePlayIcon, name: t('googlePlay')};
        if (lowerUrl.includes("epicgames.com")) return {icon: WebIcon, name: t('epicGames')}
        if (lowerUrl.includes("bsky.app")) return {icon: WebIcon, name: t('Bluesky')}

        return {icon: WebIcon, name: t('Official')}; // Any other website is considered to be the official website of the game
    };

    const getDeveloperName = () => {
        if (!gameDetails?.involved_companies) return t('unknown');
        const developer = gameDetails.involved_companies.find(ic => ic.developer);
        return developer?.company.name || t('unknown');
    };

    const formatDate = (timestamp?: number) => {
        if (!timestamp) return t('tbd');
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

        console.error(`Failed to get API response : ${error}`);
        return (
            <SafeAreaProvider style={{
                flex: 1,
                backgroundColor: colors.background,
                justifyContent: "center",
                alignItems: "center",
                paddingHorizontal: 20
            }}>
                <GamepadIcon width={90} height={60} color={colors.icon}/>
                <Text style={{
                    fontSize: 14,
                    color: colors.secondaryText,
                    marginBottom: 20,
                    textAlign: "center",
                    fontWeight: "semibold"
                }}>No Data Available</Text>
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
            <ScrollView showsVerticalScrollIndicator={false} contentContainerStyle={{paddingBottom: bottom}}>

                <View>
                    <Image source={coverUrl} resizeMode="cover" style={{width: "100%", height: 300}}/>
                    <View style={{position: "absolute", bottom: 0, right: 20, marginBottom: -20}}>
                        <LikeButton gameId={gameId}/>
                    </View>
                </View>
                <View>
                    <View style={{flexDirection: "row", marginStart: 18}}>
                        <Image
                            source={titlePictureUrl}
                            resizeMode="cover"
                            style={{width: 100, height: 150, borderRadius: 8, marginRight: 15, marginTop: -26}}
                        />
                        <View style={{flex: 1, justifyContent: "center"}}>
                            <Text style={{fontSize: 20, fontWeight: "bold", color: colors.text}}>
                                {gameDetails.name}
                            </Text>
                            <Text style={{fontSize: 14, color: colors.secondaryText, marginTop: 8}}>
                                {formatDate(gameDetails.first_release_date)} {gameDetails.first_release_date ? getRelativeTimeTextUseCase(gameDetails.first_release_date!) : ""}
                            </Text>
                            <Text style={{fontSize: 14, color: colors.secondaryText, marginTop: 4}}>
                                {getDeveloperName()}
                            </Text>
                        </View>
                    </View>


                    {gameDetails.videos && gameDetails.videos.length > 0 && (
                        <View style={{
                            backgroundColor: colors.card,
                            paddingTop: 14,
                            paddingStart: 14,
                            paddingBottom: 14,
                            elevation: 2,
                            borderTopWidth: 12,
                            borderTopColor: colors.border,
                        }}>
                            <Text style={{
                                fontSize: 18,
                                fontWeight: "semibold",
                                color: colors.text,
                                marginBottom: 10
                            }}>{t('videos')}</Text>
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
                                            <View>
                                                <Image
                                                    source={{uri: getYouTubeThumbnailUrl(video.video_id)}}
                                                    resizeMode="cover"
                                                    style={{width: 200, height: 120, borderRadius: 6}}
                                                />
                                                <View style={{
                                                    position: "absolute",
                                                    top: 0,
                                                    left: 0,
                                                    right: 0,
                                                    bottom: 0,
                                                    justifyContent: "center",
                                                    alignItems: "center"
                                                }}>
                                                    <View style={{
                                                        width: 48,
                                                        height: 48,
                                                        borderRadius: 28,
                                                        borderWidth: 2,
                                                        borderColor: "#FFF",
                                                        justifyContent: "center",
                                                        alignItems: "center",
                                                        backgroundColor: "rgba(0, 0, 0, 0.3)"
                                                    }}>
                                                        <PlayIcon width={36} height={36} fill="#FFF"/>
                                                    </View>
                                                </View>
                                            </View>
                                            <Text style={{
                                                fontSize: 13,
                                                color: colors.secondaryText,
                                                marginTop: 4,
                                                width: 200
                                            }}
                                                  numberOfLines={1}>
                                                {video.name || t('watchVideo')}
                                            </Text>
                                        </View>
                                    </TouchableOpacity>
                                ))}
                            </ScrollView>
                        </View>
                    )}

                    {gameDetails.screenshots && gameDetails.screenshots.length > 0 && (
                        <View style={{
                            backgroundColor: colors.card,
                            paddingTop: 14,
                            paddingStart: 14,
                            paddingBottom: 14,
                            elevation: 2,
                            borderTopWidth: 12,
                            borderTopColor: colors.border,
                        }}>
                            <Text style={{fontSize: 18, fontWeight: "semibold", color: colors.text, marginBottom: 10}}>
                                {t('screenshots')}
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

                    {gameDetails.summary && (
                        <TouchableOpacity
                            activeOpacity={0.7}
                            onPress={toggleSummary}
                        >
                            <View style={{
                                backgroundColor: colors.card,
                                paddingTop: 14,
                                paddingStart: 14,
                                paddingBottom: 14,
                                elevation: 2,
                                borderTopWidth: 12,
                                borderTopColor: colors.border,
                            }}>
                                <Text style={{
                                    fontSize: 18,
                                    fontWeight: "semibold",
                                    color: colors.text,
                                    marginBottom: 10
                                }}>
                                    {t('summary')}
                                </Text>
                                <Text style={{fontSize: 14, color: colors.secondaryText, lineHeight: 22, marginEnd: 8}}>
                                    {isSummaryExpanded
                                        ? gameDetails.summary
                                        : gameDetails.summary.slice(0, 100) + (gameDetails.summary.length > 100 ? "..." : "")
                                    }
                                </Text>
                            </View>
                        </TouchableOpacity>
                    )}

                    {(gameDetails.genres || gameDetails.platforms || gameDetails.game_modes ||
                        gameDetails.player_perspectives || gameDetails.themes) && (
                        <View style={{
                            backgroundColor: colors.card,
                            paddingTop: 14,
                            paddingStart: 14,
                            paddingBottom: 14,
                            elevation: 2,
                            borderTopWidth: 12,
                            borderTopColor: colors.border,
                        }}>
                            <Text style={{fontSize: 18, fontWeight: "semibold", color: colors.text, marginBottom: 10}}>
                                {t('details')}
                            </Text>

                            {gameDetails.genres && gameDetails.genres.length > 0 && (
                                <View style={{marginBottom: 12, marginEnd: 2}}>
                                    <Text
                                        style={{fontSize: 14, fontWeight: "600", color: colors.text, marginBottom: 4}}>
                                        {t('genres')}
                                    </Text>
                                    <Text style={{fontSize: 14, color: colors.secondaryText}}>
                                        {gameDetails.genres.map(g => g.name).join(". ")}
                                    </Text>
                                </View>
                            )}

                            {gameDetails.platforms && gameDetails.platforms.length > 0 && (
                                <View style={{marginBottom: 12, marginEnd: 2}}>
                                    <Text
                                        style={{fontSize: 14, fontWeight: "600", color: colors.text, marginBottom: 4}}>
                                        {t('platforms')}
                                    </Text>
                                    <Text style={{fontSize: 14, color: colors.secondaryText}}>
                                        {gameDetails.platforms.map(p => p.name).join(". ")}
                                    </Text>
                                </View>
                            )}

                            {gameDetails.game_modes && gameDetails.game_modes.length > 0 && (
                                <View style={{marginBottom: 12, marginEnd: 2}}>
                                    <Text
                                        style={{fontSize: 14, fontWeight: "600", color: colors.text, marginBottom: 4}}>
                                        {t('modes')}
                                    </Text>
                                    <Text style={{fontSize: 14, color: colors.secondaryText}}>
                                        {gameDetails.game_modes.map(m => m.name).join(". ")}
                                    </Text>
                                </View>
                            )}

                            {gameDetails.player_perspectives && gameDetails.player_perspectives.length > 0 && (
                                <View style={{marginBottom: 12, marginEnd: 2}}>
                                    <Text
                                        style={{fontSize: 14, fontWeight: "600", color: colors.text, marginBottom: 4}}>
                                        {t('playerPerspectives')}
                                    </Text>
                                    <Text style={{fontSize: 14, color: colors.secondaryText}}>
                                        {gameDetails.player_perspectives.map(p => p.name).join(". ")}
                                    </Text>
                                </View>
                            )}

                            {gameDetails.themes && gameDetails.themes.length > 0 && (
                                <View style={{marginBottom: 12, marginEnd: 2}}>
                                    <Text
                                        style={{fontSize: 14, fontWeight: "600", color: colors.text, marginBottom: 4}}>
                                        {t('themes')}
                                    </Text>
                                    <Text style={{fontSize: 14, color: colors.secondaryText}}>
                                        {gameDetails.themes.map(t => t.name).join(". ")}
                                    </Text>
                                </View>
                            )}
                        </View>
                    )}

                    {gameDetails.websites && gameDetails.websites.length > 0 && (
                        <View style={{
                            backgroundColor: colors.card,
                            paddingTop: 14,
                            paddingStart: 14,
                            paddingBottom: 14,
                            elevation: 2,
                            borderTopWidth: 12,
                            borderTopColor: colors.border,
                        }}>
                            <Text style={{fontSize: 18, fontWeight: "bold", color: colors.text, marginBottom: 10}}>
                                {t('links')}
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
                        <View style={{
                            backgroundColor: colors.card,
                            paddingTop: 14,
                            paddingStart: 14,
                            paddingBottom: 14,
                            elevation: 2,
                            borderTopWidth: 12,
                            borderTopColor: colors.border,
                        }}>
                            <Text style={{fontSize: 18, fontWeight: "bold", color: colors.text, marginBottom: 10}}>
                                {t('similarGames')}
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