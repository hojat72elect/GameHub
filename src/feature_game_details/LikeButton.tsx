import React, {useCallback, useRef} from "react";
import {Animated, Easing, TouchableWithoutFeedback} from "react-native";
import Svg, {Path} from "react-native-svg";
import {useLikes} from "@/src/shared/states/LikesContext";

const HEART_PATH =
    "M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z";

const HEART_COLOR = "#FF4B7D";
const HEART_OUTLINE_COLOR = "#999";

export function LikeButton({gameId, size = 32}: {
    gameId: string;
    size?: number;
}) {
    const {getLikedGames, toggleLikedGames} = useLikes();
    const isLiked = getLikedGames(gameId);
    const animationStateReference = useRef(false);

    // Animation values
    const fillOpacity = useRef(new Animated.Value(isLiked ? 1 : 0)).current;
    const outlineOpacity = useRef(new Animated.Value(isLiked ? 0 : 1)).current;
    const scale = useRef(new Animated.Value(1)).current;
    const breakProgress = useRef(new Animated.Value(0)).current;

    const handlePress = useCallback(() => {
        if (animationStateReference.current) return; // the previous animation of the button is not finished
        animationStateReference.current = true;

        const wasLiked = isLiked;
        toggleLikedGames(gameId);

        if (!wasLiked) {
            // user wants to like the animation
            fillOpacity.setValue(0);
            outlineOpacity.setValue(0);
            breakProgress.setValue(0);

            Animated.parallel([
                Animated.timing(fillOpacity, {
                    toValue: 1,
                    duration: 350,
                    easing: Easing.out(Easing.ease),
                    useNativeDriver: true,
                }),
                Animated.sequence([
                    Animated.timing(scale, {
                        toValue: 1.35,
                        duration: 180,
                        easing: Easing.out(Easing.back(1.5)),
                        useNativeDriver: true,
                    }),
                    Animated.timing(scale, {
                        toValue: 1,
                        duration: 220,
                        easing: Easing.inOut(Easing.ease),
                        useNativeDriver: true,
                    }),
                ]),
            ]).start(() => {
                animationStateReference.current = false;
            });
        } else {
            // User wants to unlike the animation
            breakProgress.setValue(0);

            Animated.sequence([
                Animated.parallel([
                    Animated.timing(scale, {
                        toValue: 0.7,
                        duration: 300,
                        easing: Easing.in(Easing.ease),
                        useNativeDriver: true,
                    }),
                    Animated.timing(fillOpacity, {
                        toValue: 0,
                        duration: 300,
                        easing: Easing.in(Easing.ease),
                        useNativeDriver: true,
                    }),
                    Animated.sequence([
                        Animated.timing(breakProgress, {
                            toValue: 0.5,
                            duration: 100,
                            useNativeDriver: true,
                        }),
                        Animated.timing(breakProgress, {
                            toValue: 1,
                            duration: 200,
                            useNativeDriver: true,
                        }),
                    ]),
                ]),
                Animated.parallel([
                    Animated.timing(outlineOpacity, {
                        toValue: 1,
                        duration: 250,
                        easing: Easing.out(Easing.ease),
                        useNativeDriver: true,
                    }),
                    Animated.spring(scale, {
                        toValue: 1,
                        friction: 5,
                        tension: 80,
                        useNativeDriver: true,
                    }),
                ]),
            ]).start(() => {
                animationStateReference.current = false;
            });
        }
    }, [isLiked, gameId, toggleLikedGames, fillOpacity, outlineOpacity, scale, breakProgress]);

    const shakeRotation = breakProgress.interpolate({
        inputRange: [0, 0.25, 0.5, 0.75, 1],
        outputRange: ["0deg", "-8deg", "8deg", "-4deg", "0deg"],
    });

    return (
        <TouchableWithoutFeedback onPress={handlePress}>
            <Animated.View
                style={{
                    width: size + 16,
                    height: size + 16,
                    justifyContent: "center",
                    alignItems: "center",
                    transform: [{scale}, {rotate: shakeRotation}],
                }}
            >
                <Animated.View
                    style={{
                        position: "absolute",
                        opacity: outlineOpacity,
                    }}
                >
                    <Svg width={size} height={size} viewBox="0 0 24 24">
                        <Path
                            d={HEART_PATH}
                            fill="none"
                            stroke={HEART_OUTLINE_COLOR}
                            strokeWidth={1.8}
                        />
                    </Svg>
                </Animated.View>

                <Animated.View
                    style={{
                        position: "absolute",
                        opacity: fillOpacity,
                    }}
                >
                    <Svg width={size} height={size} viewBox="0 0 24 24">
                        <Path
                            d={HEART_PATH}
                            fill={HEART_COLOR}
                        />
                    </Svg>
                </Animated.View>
            </Animated.View>
        </TouchableWithoutFeedback>
    );
}
