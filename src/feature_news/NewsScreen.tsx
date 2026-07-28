import {FlatList, Image, Linking, Text, TouchableOpacity, View} from "react-native";
import idleImage from "@/assets/images/game_landscape_placeholder.webp";
import {SafeAreaProvider} from "react-native-safe-area-context";
import {useEffect, useState} from "react";
import mockedNewsApiResponse from "../feature_news/api/mocked_news_api_response.json";
import {NewsApiResultItem} from "@/src/feature_news/domain/NewsApiResultItem";
import {useTheme} from "@/src/ThemeContext";

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
    const {colors} = useTheme();

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
            style={{
                backgroundColor: colors.card,
                marginBottom: 25,
                overflow: "hidden",
                borderWidth: 1,
                borderColor: colors.border,
                borderRadius: 8
            }}
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
                    color: colors.text,
                    lineHeight: 26,
                    marginBottom: 8
                }}>{item.title}</Text>
                <Text numberOfLines={3}
                      style={{fontSize: 16, color: colors.secondaryText, lineHeight: 22, marginBottom: 10}}
                >{item.deck}</Text>
                <View>
                    <Text style={{
                        fontSize: 14,
                        color: colors.secondaryText
                    }}>🕒 {new Date(item.publish_date).toLocaleDateString()}</Text>
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

            <FlatList
                data={newsData || []}
                renderItem={({item}) => <NewsCard {...item}/>}
                keyExtractor={(item) => String(item.id)}
                showsVerticalScrollIndicator={true}
                contentContainerStyle={{paddingBottom: 80, padding:20}}
            />
        </SafeAreaProvider>
    );
}