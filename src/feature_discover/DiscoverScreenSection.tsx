import {GamesCategory} from "@/src/feature_discover/domain/GamesCategory";
import {Game} from "@/src/feature_discover/domain/Game";
import {Image, ScrollView, Text, TouchableOpacity, View} from "react-native";
import idleImage from "@/assets/images/game_portrait_placeholder.webp";
import {router} from "expo-router";
import {useTheme} from "@/src/ThemeContext";

/**
 * Each one of the seemingly identical sections you see in the discover screen.
 * Right now, we have these 4 sections :
 *
 * 1 - Popular
 * 2 - Recently released
 * 3 - Coming Soon
 * 4 - Most anticipated
 */
export function DiscoverScreenSection({title, data}: { title: GamesCategory, data: Game[] }) {
    const {colors} = useTheme();

    return (<View style={{marginBottom: 25}}>
        <View style={{
            flexDirection: "row",
            justifyContent: "space-between",
            alignItems: "center",
            paddingHorizontal: 15,
            marginBottom: 10
        }}>
            <Text style={{fontSize: 22, fontWeight: "bold", fontFamily: "serif", color: colors.text}}>{(() => {
                switch (title) {
                    case GamesCategory.Popular:
                        return "Popular";
                    case GamesCategory.RecentlyReleased:
                        return "Recently Released";
                    case GamesCategory.ComingSoon:
                        return "Coming Soon";
                    case GamesCategory.MostAnticipated:
                        return "Most Anticipated"
                }
            })()}</Text>
            <TouchableOpacity>
                <Text
                    style={{color: "#FF4B7D", fontWeight: "600", fontSize: 14}}
                    onPress={() => router.push({pathname: '/category-games', params: {category: title}})}
                >SEE ALL</Text>
            </TouchableOpacity>
        </View>

        <ScrollView horizontal={true} showsHorizontalScrollIndicator={false} contentContainerStyle={{paddingLeft: 12}}>
            {data.map((item) => {
                const coverUrl = item.cover?.image_id
                    ? {uri: `https://images.igdb.com/igdb/image/upload/t_cover_big/${item.cover.image_id}.jpg`}
                    : idleImage;
                return (
                    <TouchableOpacity
                        key={item.id}
                        style={{
                            marginRight: 12,
                            borderRadius: 8,
                            overflow: "hidden",
                            backgroundColor: colors.placeholder
                        }}
                        onPress={() => router.push({pathname: '/game-details', params: {gameId: item.id}})}
                    >
                        <Image source={coverUrl} resizeMode="cover" style={{width: 110, height: 180}}/>
                    </TouchableOpacity>
                );
            })}
        </ScrollView>
    </View>);
}