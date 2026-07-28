import {Text, View} from "react-native";
import React from "react";
import {useTheme} from "@/src/ThemeContext";

export function LikesScreen(): React.JSX.Element {
    const {colors} = useTheme();

    return (
        <View style={{flex: 1, justifyContent: "center", alignItems: "center", backgroundColor: colors.background}}>
            <Text style={{color: colors.text}}>Hello from new Likes Screen</Text>
        </View>
    );
}