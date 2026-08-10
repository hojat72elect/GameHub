import {Linking, TouchableOpacity} from "react-native";
import React, {useEffect} from "react";
import Animated, {useAnimatedStyle, useSharedValue, withSequence, withTiming} from "react-native-reanimated";

export function BuyCoffeeButton() {
    const rotation = useSharedValue(0);

    const shake = () => {
        rotation.value = withSequence(
            withTiming(10, {duration: 100}),
            withTiming(-10, {duration: 100}),
            withTiming(10, {duration: 100}),
            withTiming(-10, {duration: 100}),
            withTiming(0, {duration: 100})
        );
    };

    useEffect(() => {
        shake();
        const shakeInterval = setInterval(shake, 7_000);
        return () => {
            clearInterval(shakeInterval);
        };
    }, []);

    const animatedStyle = useAnimatedStyle(() => ({
        transform: [{rotate: `${rotation.value}deg`}]
    }));

    return (
        <TouchableOpacity
            style={{
                marginTop: 20,
                alignSelf: "center",
            }}
            onPress={() => Linking.openURL("https://buymeacoffee.com/hojat")}
        >
            <Animated.Image
                source={require("@/assets/images/buy_me_a_coffee_yellow_button.png")}
                style={[
                    {
                        width: 200,
                        height: 50,
                        resizeMode: "contain"
                    },
                    animatedStyle
                ]}
            />
        </TouchableOpacity>
    );
}
