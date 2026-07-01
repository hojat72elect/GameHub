import {SafeAreaProvider} from "react-native-safe-area-context";
import {Text, View} from "react-native";
import {useLocalSearchParams} from "expo-router";

export function GameDetailsScreen() {

    const {gameId} = useLocalSearchParams<{ gameId: string }>();

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: "#FFF"}}>
            <View style={{flex: 1, justifyContent: "center", alignItems: "center"}}>
                <Text style={{fontSize: 20, fontWeight: "bold", color: "#333"}}>{`Game details : ${gameId}`}</Text>
            </View>
        </SafeAreaProvider>
    );
}