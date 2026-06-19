import {ActivityIndicator, Image, RefreshControl, ScrollView, Text, TouchableOpacity, View} from "react-native";
import idleImage from "@/assets/images/game_portrait_placeholder.webp";
import {SafeAreaProvider} from 'react-native-safe-area-context';
import SearchIcon from "@/assets/svg/magnify.svg";
import React from "react";
import {fetchDiscoverData, Game} from "./services/igdbService";

/**
 * Each one of the seemingly identical sections you see in the discover screen.
 * Right now, we have these 4 sections :
 *
 * 1 - Popular
 * 2 - Recently released
 * 3 - Coming Soon
 * 4 - Most anticipated
 */
function DiscoverScreenSection({title, data}: { title: string, data: Game[] }) {
    return (<View style={{marginBottom: 25}}>
        <View style={{
            flexDirection: "row",
            justifyContent: "space-between",
            alignItems: "center",
            paddingHorizontal: 15,
            marginBottom: 10
        }}>
            <Text style={{fontSize: 22, fontWeight: "bold", fontFamily: "serif", color: "#333"}}>{title}</Text>
            <TouchableOpacity>
                <Text style={{color: "#FF4B7D", fontWeight: "600", fontSize: 14}}>SEE ALL</Text>
            </TouchableOpacity>
        </View>

        <ScrollView horizontal={true} showsHorizontalScrollIndicator={false} contentContainerStyle={{paddingLeft: 12}}>
            {data.map((item) => {
                const coverUrl = item.cover?.image_id
                    ? {uri: `https://images.igdb.com/igdb/image/upload/t_cover_big/${item.cover.image_id}.jpg`}
                    : idleImage;
                return (
                    <TouchableOpacity key={item.id} style={{
                        marginRight: 12,
                        borderRadius: 8,
                        overflow: "hidden",
                        backgroundColor: "#EEE"
                    }}>
                        <Image source={coverUrl} resizeMode="cover" style={{width: 110, height: 180}}/>
                    </TouchableOpacity>
                );
            })}
        </ScrollView>
    </View>);
}

export function DiscoverScreen(): React.JSX.Element {
    const [popular, setPopular] = React.useState<Game[]>([]);
    const [recent, setRecent] = React.useState<Game[]>([]);
    const [soon, setSoon] = React.useState<Game[]>([]);
    const [anticipated, setAnticipated] = React.useState<Game[]>([]);
    const [isLoading, setIsLoading] = React.useState<boolean>(true);
    const [refreshing, setRefreshing] = React.useState<boolean>(false);
    const [error, setError] = React.useState<string | null>(null);

    const loadData = async (showRefreshIndicator = false) => {
        if (showRefreshIndicator) {
            setRefreshing(true);
        } else {
            setIsLoading(true);
        }
        setError(null);
        try {
            const data = await fetchDiscoverData();
            setPopular(data.popular);
            setRecent(data.recent);
            setSoon(data.soon);
            setAnticipated(data.anticipated);
        } catch (err: any) {
            console.error("Error loading IGDB discover data:", err);
            setError(err.message || "Failed to load discover data");
        } finally {
            setIsLoading(false);
            setRefreshing(false);
        }
    };

    React.useEffect(() => {
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
                <TouchableOpacity>
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
                <DiscoverScreenSection title="Popular" data={popular}/>
                <DiscoverScreenSection title="Recently Released" data={recent}/>
                <DiscoverScreenSection title="Coming Soon" data={soon}/>
                <DiscoverScreenSection title="Most Anticipated" data={anticipated}/>
            </ScrollView>
        </SafeAreaProvider>
    );
}

