import {ActivityIndicator, RefreshControl, ScrollView, Text, TouchableOpacity, View} from "react-native";
import {SafeAreaProvider} from 'react-native-safe-area-context';
import SearchIcon from "@/assets/svg/magnify.svg";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Game} from "@/src/shared/domain/Game";
import {router} from "expo-router";
import {GamesCategory} from "@/src/shared/domain/GamesCategory";
import {DiscoverScreenSection} from "@/src/feature_discover/DiscoverScreenSection";
import {GamesGeneralRepository} from "@/src/feature_discover/data/GamesGeneralRepository";
import {useTheme} from "@/src/shared/contexts/ThemeContext";

/**
 * The UI of the Discover screen. This is always the first screen that user sees after opening the app.
 */
export function DiscoverScreen() {
    const [popularGames, setPopularGames] = useState<Game[]>([]);
    const [recentGames, setRecentGames] = useState<Game[]>([]);
    const [comingSoonGames, setComingSoonGames] = useState<Game[]>([]);
    const [anticipatedGames, setAnticipatedGames] = useState<Game[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [refreshing, setRefreshing] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const {colors} = useTheme();
    const {t} = useTranslation();

    async function loadData(showRefreshIndicator = false) {
        if (showRefreshIndicator) {
            setRefreshing(true);
        } else {
            setIsLoading(true);
        }
        setError(null);
        try {
            const data = await GamesGeneralRepository.getDiscoverScreenGames();
            setPopularGames(data.popularGames);
            setRecentGames(data.recentlyReleasedGames);
            setComingSoonGames(data.comingSoonGames);
            setAnticipatedGames(data.mostAnticipatedGames);
        } catch (error: any) {
            console.error("Error loading IGDB discover data:", error);
            setError(error.message || "Failed to load discover data");
        } finally {
            setIsLoading(false);
            setRefreshing(false);
        }
    }

    useEffect(() => {
        loadData();
    }, []);

    if (isLoading && !refreshing) {
        return (
            <SafeAreaProvider
                style={{flex: 1, backgroundColor: colors.background, justifyContent: "center", alignItems: "center"}}>
                <ActivityIndicator size="large" color="#FF4B7D"/>
            </SafeAreaProvider>
        );
    }

    if (error && !refreshing) {
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
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
                paddingHorizontal: 20,
                paddingBottom: 14,
                paddingTop: 24,
                borderBottomWidth: 1,
                borderBottomColor: colors.border
            }}>
                <Text style={{fontSize: 24, fontWeight: "400", color: colors.text}}>{t('discoverTitle')}</Text>
                <TouchableOpacity onPress={() => router.push('/search')}>
                    <SearchIcon width={24} height={24} color={colors.text}/>
                </TouchableOpacity>
            </View>

            <ScrollView
                showsVerticalScrollIndicator={false}
                refreshControl={
                    <RefreshControl refreshing={refreshing} onRefresh={() => loadData(true)} colors={["#FF4B7D"]}
                                    tintColor="#FF4B7D"/>
                }
            >
                <DiscoverScreenSection title={GamesCategory.Popular} data={popularGames}/>
                <DiscoverScreenSection title={GamesCategory.RecentlyReleased} data={recentGames}/>
                <DiscoverScreenSection title={GamesCategory.ComingSoon} data={comingSoonGames}/>
                <DiscoverScreenSection title={GamesCategory.MostAnticipated} data={anticipatedGames}/>
            </ScrollView>
        </SafeAreaProvider>
    );
}
