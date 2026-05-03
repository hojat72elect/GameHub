import {FlatList, Image, Linking, Text, TouchableOpacity, View} from "react-native";
import idleImage from "@/assets/images/game_landscape_placeholder.webp";
import {SafeAreaProvider} from "react-native-safe-area-context";
import axios from 'axios';
import {NewsApiResponse} from "@/src/feature_news/domain/NewsApiResponse";
import {useEffect, useState} from "react";

async function fetchGamespotArticles(): Promise<NewsApiResponse | null> {
    const API_KEY = process.env.EXPO_PUBLIC_GAMESPOT_API_KEY!;
    const url = "http://www.gamespot.com/api/articles/";

    try {
        const response = await axios.get<NewsApiResponse>(url, {
            params: {
                api_key: API_KEY,
                format: 'json',
                sort: 'publish_date:desc',
                field_list: 'id,title,deck,publish_date,image,site_detail_url',
            },
        });

        return response.data;

    } catch (error) {
        if (axios.isAxiosError(error)) {
            console.error("Axios Error:", error.message);
        } else {
            console.error("Unexpected Error:", error);
        }
        return null;
    }
}

export function NewsScreen() {
    const [newsData, setNewsData] = useState<NewsApiResponse | null>(null);

    useEffect(() => {
        fetchGamespotArticles().then(data => {
            if (data) {
                setNewsData(data);
            }
        });
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
            style={{backgroundColor: "#FFF", marginBottom: 25, overflow: "hidden"}}
            onPress={() => Linking.openURL(item.site_detail_url)}
        >
            <Image
                source={item.image?.original ? {uri: item.image.original} : idleImage}
                resizeMode="cover"
                style={{width: "100%", height: 200}}
            />
            <View style={{padding: 15}}>
                <Text style={{
                    fontSize: 20,
                    fontWeight: "600",
                    color: "#1A1A1A",
                    lineHeight: 26,
                    marginBottom: 8
                }}>{item.title}</Text>
                <Text numberOfLines={3}
                      style={{fontSize: 16, color: "#444", lineHeight: 22, marginBottom: 10}}
                >{item.deck}</Text>
                <View>
                    <Text style={{
                        fontSize: 14,
                        color: "#888"
                    }}>🕒 {new Date(item.publish_date).toLocaleDateString()}</Text>
                </View>
            </View>
        </TouchableOpacity>
    );

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: "#FFF"}}>
            <View style={{
                paddingTop: 35,
                paddingBottom: 10,
                paddingHorizontal: 20,
                borderBottomWidth: 1,
                borderBottomColor: "#F0F0F0"
            }}>
                <Text style={{fontSize: 28, fontWeight: "300", fontFamily: "System", color: "#000"}}>News</Text>
            </View>

            <FlatList
                data={newsData?.results || []}
                renderItem={({item}) => <NewsCard {...item}/>}
                keyExtractor={(item) => String(item.id)}
                showsVerticalScrollIndicator={true}
                contentContainerStyle={{paddingBottom: 100}}
            />
        </SafeAreaProvider>
    );
}