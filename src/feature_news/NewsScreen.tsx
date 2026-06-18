import {FlatList, Image, Linking, Text, TouchableOpacity, View} from "react-native";
import idleImage from "@/assets/images/game_landscape_placeholder.webp";
import {SafeAreaProvider} from "react-native-safe-area-context";
import {useEffect, useState} from "react";
import mockedNewsApiResponse from "../feature_news/api/mocked_news_api_response.json";
import {NewsApiResultItem} from "@/src/feature_news/domain/NewsApiResultItem";

function fetchGamespotArticles(): NewsApiResultItem[] {

    const mapJsonToNewsItems = (rawItems: typeof mockedNewsApiResponse): NewsApiResultItem[] => {
        return rawItems.map((item, index) => ({
            id: index,
            title: item.title,
            deck: item.lede,
            publish_date: item.release_time,
            site_detail_url: item.link,
            image: {
                square_tiny: item.image_url,
                screen_tiny: item.image_url,
                square_small: item.image_url,
                original: item.image_url,
            }
        }));

    };

    return mapJsonToNewsItems(mockedNewsApiResponse);
}

export function NewsScreen() {
    const [newsData, setNewsData] = useState<NewsApiResultItem[]>([]);

    useEffect(() => {
        setNewsData(fetchGamespotArticles());
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
                data={newsData || []}
                renderItem={({item}) => <NewsCard {...item}/>}
                keyExtractor={(item) => String(item.id)}
                showsVerticalScrollIndicator={true}
                contentContainerStyle={{paddingBottom: 100}}
            />
        </SafeAreaProvider>
    );
}