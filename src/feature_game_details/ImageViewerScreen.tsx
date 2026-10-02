import {SafeAreaProvider, useSafeAreaInsets} from "react-native-safe-area-context";
import {Alert, Text, ToastAndroid, TouchableOpacity, View} from "react-native";
import {useLocalSearchParams, useRouter} from "expo-router";
import {useState} from "react";
import {useTheme} from "@/src/shared/contexts/ThemeContext";
import {isAvailableAsync, shareAsync} from 'expo-sharing';
import {downloadAsync,} from 'expo-file-system/legacy';
import {Paths} from 'expo-file-system';
import {createAssetAsync, requestPermissionsAsync} from 'expo-media-library';
import ShareIcon from "@/assets/svg/share.svg";
import DownloadIcon from "@/assets/svg/download.svg";
import ImageViewer from "react-native-image-zoom-viewer";

export function ImageViewerScreen() {

    const {images: imagesJson, initialIndex} = useLocalSearchParams<{
        images: string;
        initialIndex: string;
    }>();
    const router = useRouter();
    const {colors} = useTheme();
    const {top} = useSafeAreaInsets();

    const images: {
        id: number;
        image_id: string; // The id of that image on IGDB servers
    }[] = JSON.parse(imagesJson);
    const initialIndexNum = parseInt(initialIndex, 10);
    const [currentIndex, setCurrentIndex] = useState(initialIndexNum);

    const getCoverUrl = (imageId: string, size: "cover_big" | "1080p" = "1080p") => {
        return `https://images.igdb.com/igdb/image/upload/t_${size}/${imageId}.jpg`;
    };

    const getCurrentImageUrl = () => {
        return getCoverUrl(images[currentIndex].image_id, "1080p");
    };

    const handleShare = async () => {
        try {
            const imageUrl = getCurrentImageUrl();
            const fileUri = `${Paths.cache.uri}screenshot_${images[currentIndex].image_id}.jpg`;

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
            const fileUri = `${Paths.cache.uri}screenshot_${images[currentIndex].image_id}.jpg`;

            const downloadResult = await downloadAsync(imageUrl, fileUri);

            if (downloadResult.status === 200) {
                const {status} = await requestPermissionsAsync();
                if (status === 'granted') {
                    await createAssetAsync(fileUri);
                    ToastAndroid.show("Image saved to your gallery successfully!", ToastAndroid.SHORT);
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

                    <View style={{flexDirection: 'row', gap: 16}}>
                        <TouchableOpacity style={{padding: 5}} onPress={handleShare}>
                            <ShareIcon width={30} height={30}/>
                        </TouchableOpacity>
                        <TouchableOpacity style={{padding:5}} onPress={handleDownload}>
                           <DownloadIcon width={30} height={30}/>
                        </TouchableOpacity>
                    </View>
                </View>

                <ImageViewer
                    imageUrls={images.map(image => ({url: getCoverUrl(image.image_id, "1080p")}))}
                    index={initialIndexNum}
                    onChange={(index) => index !== undefined && setCurrentIndex(index)}
                    style={{flex: 1}}
                    backgroundColor="#000"
                />
            </View>
        </SafeAreaProvider>
    );
}
