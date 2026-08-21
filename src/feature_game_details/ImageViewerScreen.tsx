import {SafeAreaProvider, useSafeAreaInsets} from "react-native-safe-area-context";
import {Dimensions, Image, ScrollView, Text, TouchableOpacity, View} from "react-native";
import {useLocalSearchParams, useRouter} from "expo-router";
import {useRef, useState} from "react";
import {useTheme} from "@/src/shared/contexts/ThemeContext";
import {NativeSyntheticEvent} from "react-native/Libraries/Types/CoreEventTypes";
import {NativeScrollEvent} from "react-native/Libraries/Components/ScrollView/ScrollView";

export function ImageViewerScreen() {

    const {screenshots: screenshotsJson, initialIndex} = useLocalSearchParams<{
        screenshots: string;
        initialIndex: string;
    }>();
    const router = useRouter();
    const {colors} = useTheme();
    const {top} = useSafeAreaInsets();
    const scrollViewRef = useRef<ScrollView>(null);

    const screenshots: {
        id: number;
        image_id: string; // The id of that image on IGDB servers
    }[] = JSON.parse(screenshotsJson);
    const initialIndexNum = parseInt(initialIndex, 10);
    const [currentIndex, setCurrentIndex] = useState(initialIndexNum);

    const getCoverUrl = (imageId: string, size: "cover_big" | "1080p" = "1080p") => {
        return `https://images.igdb.com/igdb/image/upload/t_${size}/${imageId}.jpg`;
    };

    const handleScroll = (event: NativeSyntheticEvent<NativeScrollEvent>) => {
        const contentOffsetX = event.nativeEvent.contentOffset.x;
        const width = event.nativeEvent.layoutMeasurement.width;
        const newIndex = Math.round(contentOffsetX / width);
        setCurrentIndex(newIndex);
    };

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: colors.background}}>
            <View style={{flex: 1}}>
                <View style={{
                    position: 'absolute',
                    top: 0,
                    left: 0,
                    right: 0,
                    zIndex: 10,
                    flexDirection: 'row',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    paddingTop: top,
                    paddingBottom: 16,
                    paddingHorizontal: 16,
                    backgroundColor: 'rgba(0, 0, 0, 0.5)',
                }}>
                    <TouchableOpacity onPress={()=>{
                        router.back();
                    }}>
                        <Text style={{fontSize: 24, color: '#FFF'}}>✕</Text>
                    </TouchableOpacity>
                    <Text style={{fontSize: 16, color: '#FFF', fontWeight: '600'}}>
                        {currentIndex + 1} / {screenshots.length}
                    </Text>
                    <View style={{width: 24}}/>
                </View>

                <ScrollView
                    ref={scrollViewRef}
                    horizontal
                    pagingEnabled
                    showsHorizontalScrollIndicator={false}
                    style={{flex: 1}}
                    onMomentumScrollEnd={handleScroll}
                    onLayout={() => {
                        setTimeout(() => {
                            scrollViewRef.current?.scrollTo({
                                x: initialIndexNum * Dimensions.get('window').width,
                                animated: false
                            });
                        }, 0);
                    }}
                >
                    {screenshots.map((screenshot) => (
                        <View
                            key={screenshot.id}
                            style={{
                                width: Dimensions.get('window').width,
                                height: Dimensions.get('window').height,
                                justifyContent: 'center',
                                alignItems: 'center',
                                backgroundColor: '#000'
                            }}
                        >
                            <Image
                                source={{uri: getCoverUrl(screenshot.image_id, "1080p")}}
                                resizeMode="contain"
                                style={{width: '100%', height: '100%'}}
                            />
                        </View>
                    ))}
                </ScrollView>
            </View>
        </SafeAreaProvider>
    );
}
