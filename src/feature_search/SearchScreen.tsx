import {SafeAreaProvider} from "react-native-safe-area-context";
import {Text, View} from "react-native";
import {useTheme} from "@/src/ThemeContext";

export function SearchScreen() {
    const {colors} = useTheme();

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: colors.background}}>
            <View style={{flex: 1, justifyContent: "center", alignItems: "center"}}>
                <Text style={{fontSize: 20, fontWeight: "bold", color: colors.text}}>Search Screen</Text>
            </View>
        </SafeAreaProvider>
    );
}