import {SafeAreaProvider} from "react-native-safe-area-context";
import {Text, View} from "react-native";
import {useLocalSearchParams} from "expo-router";
import {GameCategory} from "@/src/feature_discover/domain/GameCategory";

export function CategoryGamesScreen() {
    const {category} = useLocalSearchParams<{ category?: string }>();

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: "#FFF"}}>
            <View style={{flex: 1, justifyContent: "center", alignItems: "center"}}>
                <Text style={{fontSize: 20, fontWeight: "bold", color: "#333"}}>{(() => {
                    switch (category) {
                        case GameCategory.Popular.toString():
                            return "popular games";
                        case GameCategory.RecentlyReleased.toString():
                            return "Recently released games";
                        case GameCategory.ComingSoon.toString():
                            return "Coming soon games";
                        case GameCategory.MostAnticipated.toString():
                            return "Most anticipated games";
                    }
                })()}</Text>
            </View>
        </SafeAreaProvider>
    );
}