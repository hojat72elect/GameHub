import {ActivityIndicator, FlatList, Image, Linking, Text, TouchableOpacity, View} from "react-native";
import idleImage from "@/assets/images/game_landscape_placeholder.webp";
import {SafeAreaProvider} from "react-native-safe-area-context";
import {useEffect, useState} from "react";
import axios from "axios";
import {NewsApiResultItem} from "@/src/feature_news/domain/NewsApiResultItem";
import {useTheme} from "@/src/ThemeContext";
import {GAMESPOT_FEED_URL, getRemoteArticlesUseCase} from "@/src/feature_news/api/getRemoteArticlesUseCase";
import ClockIcon from "@/assets/svg/clock_outline_16dp.svg";

export function NewsScreen() {
    const [newsData, setNewsData] = useState<NewsApiResultItem[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const {colors} = useTheme();

    const fetchFeed = async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await axios.get(GAMESPOT_FEED_URL);
            const parsedItems = getRemoteArticlesUseCase(response.data);
            setNewsData(parsedItems);
        } catch (err: any) {
            console.error("Error fetching gamespot feed:", err);
            setError("Failed to load news feed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchFeed();
    }, []);

    const NewsCard = (item: {
        id: number;
        title: string;
        deck: string;
        publish_date: string;
        image: any;
        site_detail_url: string
    }) => (
        <TouchableOpacity
            activeOpacity={0.9}
            style={{
                backgroundColor: colors.card,
                marginBottom: 18,
                overflow: "hidden",
                borderWidth: 1,
                borderColor: colors.border,
                elevation: 2,
            }}
            onPress={() => Linking.openURL(item.site_detail_url)}
        >
            <Image
                source={item.image?.original ? {uri: item.image.original} : idleImage}
                resizeMode="cover"
                style={{width: "90%", height: 200, alignSelf: "center", borderRadius: 6, marginTop: 16}}
            />
            <View style={{padding: 15}}>
                <Text style={{
                    fontSize: 18,
                    fontWeight: "semibold",
                    color: colors.text,
                    lineHeight: 26,
                    marginBottom: 8
                }}>{item.title}</Text>
                <Text numberOfLines={3}
                      style={{fontSize: 14, color: colors.secondaryText, lineHeight: 22, marginBottom: 10}}
                >{item.deck}</Text>
                <View style={{flexDirection: "row", alignItems: "center"}}>
                    <ClockIcon width={20} height={20} fill={colors.icon}/>
                    <Text style={{
                        fontSize: 14,
                        color: colors.secondaryText,
                        paddingLeft: 5,
                    }}>{new Date(item.publish_date).toLocaleString('en-US', {
                        month: 'short',
                        day: 'numeric',
                        hour: 'numeric',
                        minute: '2-digit',
                        hour12: true
                    })}</Text>
                </View>
            </View>
        </TouchableOpacity>
    );

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: colors.background}}>
            <View style={{
                paddingTop: 35,
                paddingBottom: 10,
                paddingHorizontal: 20,
                borderBottomWidth: 1,
                borderBottomColor: colors.border
            }}>
                <Text style={{fontSize: 28, fontWeight: "300", fontFamily: "System", color: colors.text}}>News</Text>
            </View>

            {loading ? (
                <View style={{flex: 1, justifyContent: "center", alignItems: "center"}}>
                    <ActivityIndicator size="large" color={colors.text}/>
                </View>
            ) : error ? (
                <View style={{flex: 1, justifyContent: "center", alignItems: "center", padding: 20}}>
                    <Text style={{fontSize: 16, color: colors.secondaryText, textAlign: "center", marginBottom: 20}}>
                        {error}
                    </Text>
                    <TouchableOpacity
                        onPress={fetchFeed}
                        activeOpacity={0.7}
                        style={{
                            backgroundColor: colors.text,
                            paddingHorizontal: 24,
                            paddingVertical: 12,
                            borderRadius: 8
                        }}
                    >
                        <Text style={{color: colors.background, fontWeight: "600", fontSize: 16}}>Try Again</Text>
                    </TouchableOpacity>
                </View>
            ) : (
                <FlatList
                    data={newsData || []}
                    renderItem={({item}) => <NewsCard {...item}/>}
                    keyExtractor={(item) => String(item.id)}
                    showsVerticalScrollIndicator={true}
                />
            )}
        </SafeAreaProvider>
    );
}