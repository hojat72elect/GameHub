import {SafeAreaProvider, useSafeAreaInsets} from "react-native-safe-area-context";
import {Alert, Dimensions, Image, ScrollView, Text, TouchableOpacity, View} from "react-native";
import {useLocalSearchParams, useRouter} from "expo-router";
import {useRef, useState} from "react";
import {useTheme} from "@/src/shared/contexts/ThemeContext";
import {NativeSyntheticEvent} from "react-native/Libraries/Types/CoreEventTypes";
import {NativeScrollEvent} from "react-native/Libraries/Components/ScrollView/ScrollView";
import {isAvailableAsync, shareAsync} from 'expo-sharing';
import {downloadAsync,} from 'expo-file-system/legacy';
import {Paths} from 'expo-file-system';
import {createAssetAsync, requestPermissionsAsync} from 'expo-media-library';

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

    const getCurrentImageUrl = () => {
        return getCoverUrl(screenshots[currentIndex].image_id, "1080p");
    };

    const handleShare = async () => {
        try {
            const imageUrl = getCurrentImageUrl();
            const fileUri = `${Paths.cache.uri}screenshot_${screenshots[currentIndex].image_id}.jpg`;

            const downloadResult = await downloadAsync(imageUrl, fileUri);

            if (downloadResult.status === 200) {
                if (await isAvailableAsync()) {
                    await shareAsync(fileUri);
                } else {
                    Alert.alert('Error', 'Sharing is not available on this device');
                }
            }
        } catch (error) {
            Alert.alert('Error', 'Failed to share image');
            console.error('Share error:', error);
        }
    };

    const handleDownload = async () => {
        try {
            const imageUrl = getCurrentImageUrl();
            const fileUri = `${Paths.cache.uri}screenshot_${screenshots[currentIndex].image_id}.jpg`;

            const downloadResult = await downloadAsync(imageUrl, fileUri);

            if (downloadResult.status === 200) {
                const {status} = await requestPermissionsAsync();
                if (status === 'granted') {
                    await createAssetAsync(fileUri);
                    Alert.alert('Success', 'Image saved to your gallery');
                } else {
                    Alert.alert('Permission denied', 'Permission to access gallery is required');
                }
            }
        } catch (error) {
            Alert.alert('Error', 'Failed to download image');
            console.error('Download error:', error);
        }
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
                    <TouchableOpacity onPress={() => {
                        router.back();
                    }}>
                        <Text style={{fontSize: 24, color: '#FFF'}}>✕</Text>
                    </TouchableOpacity>
                    <Text style={{fontSize: 16, color: '#FFF', fontWeight: '600'}}>
                        {currentIndex + 1} / {screenshots.length}
                    </Text>
                    <View style={{flexDirection: 'row', gap: 16}}>
                        <TouchableOpacity onPress={handleShare}>
                            <Text style={{fontSize: 24, color: '#FFF'}}>↗</Text>
                        </TouchableOpacity>
                        <TouchableOpacity onPress={handleDownload}>
                            <Text style={{fontSize: 24, color: '#FFF'}}>↓</Text>
                        </TouchableOpacity>
                    </View>
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
