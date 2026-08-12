import {ActivityIndicator, Image, ScrollView, Text, TextInput, TouchableOpacity, View} from "react-native";
import {SafeAreaProvider} from "react-native-safe-area-context";
import {router} from "expo-router";
import {useState} from "react";
import {useTranslation} from "react-i18next";
import {Game} from "@/src/shared/domain/Game";
import idleImage from "@/assets/images/game_portrait_placeholder.webp";
import SearchIcon from "@/assets/svg/magnify.svg";
import {SearchGamesDatasource} from "@/src/feature_search/SearchGamesDatasource";
import {useTheme} from "@/src/shared/contexts/ThemeContext";

export function SearchScreen() {
    const [query, setQuery] = useState<string>("");
    const [results, setResults] = useState<Game[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [hasSearched, setHasSearched] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const {colors} = useTheme();
    const {t} = useTranslation();

    const performSearch = async () => {
        const trimmedQuery = query.trim();
        if (trimmedQuery.length === 0) return;

        setIsLoading(true);
        setError(null);
        setHasSearched(true);
        try {
            const data = await SearchGamesDatasource.get(trimmedQuery);
            setResults(data);
        } catch (err: any) {
            console.error("Error searching games:", err);
            setError(err.message || "Failed to search games");
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: colors.background}}>
            <View style={{
                paddingHorizontal: 20,
                paddingVertical: 15,
                borderBottomWidth: 1,
                borderBottomColor: colors.border,
                flexDirection: "row",
                alignItems: "center"
            }}>
                <TouchableOpacity onPress={() => router.back()} style={{marginRight: 15}}>
                    <Text style={{fontSize: 18, color: "#FF4B7D", fontWeight: "600"}}>{t('back')}</Text>
                </TouchableOpacity>
                <Text style={{flex: 1, fontSize: 24, fontWeight: "bold", color: colors.text}}>{t('searchTitle')}</Text>
            </View>

            <View style={{paddingHorizontal: 15, paddingVertical: 12}}>
                <View style={{
                    flexDirection: "row",
                    alignItems: "center",
                    backgroundColor: colors.card,
                    borderRadius: 8,
                    borderWidth: 1,
                    borderColor: colors.border,
                    paddingHorizontal: 12
                }}>
                    <SearchIcon width={20} height={20} color={colors.secondaryText}/>
                    <TextInput
                        style={{
                            flex: 1,
                            fontSize: 16,
                            color: colors.text,
                            paddingVertical: 12,
                            marginLeft: 10
                        }}
                        placeholder={t('placeholder')}
                        placeholderTextColor={colors.secondaryText}
                        value={query}
                        onChangeText={setQuery}
                        onSubmitEditing={performSearch}
                        returnKeyType="search"
                        autoFocus={true}
                    />
                </View>
            </View>

            {isLoading && (
                <View style={{flex: 1, justifyContent: "center", alignItems: "center"}}>
                    <ActivityIndicator size="large" color="#FF4B7D"/>
                </View>
            )}

            {error && !isLoading && (
                <View style={{flex: 1, justifyContent: "center", alignItems: "center", paddingHorizontal: 20}}>
                    <Text style={{
                        fontSize: 20,
                        fontWeight: "bold",
                        color: colors.text,
                        marginBottom: 10,
                        textAlign: "center"
                    }}>{t('somethingWentWrong')}</Text>
                    <Text style={{
                        fontSize: 14,
                        color: colors.secondaryText,
                        marginBottom: 20,
                        textAlign: "center"
                    }}>{error}</Text>
                    <TouchableOpacity onPress={performSearch} style={{
                        backgroundColor: "#FF4B7D",
                        paddingHorizontal: 25,
                        paddingVertical: 12,
                        borderRadius: 8
                    }}>
                        <Text style={{color: "#FFF", fontWeight: "600"}}>{t('retry')}</Text>
                    </TouchableOpacity>
                </View>
            )}

            {!isLoading && !error && hasSearched && results.length === 0 && (
                <View style={{flex: 1, justifyContent: "center", alignItems: "center", marginTop: 50}}>
                    <Text style={{fontSize: 16, color: colors.secondaryText}}>{t('noGamesFound')}</Text>
                </View>
            )}

            {!isLoading && !error && results.length > 0 && (
                <ScrollView
                    showsVerticalScrollIndicator={false}
                    contentContainerStyle={{padding: 15}}
                >
                    <View style={{
                        flexDirection: "row",
                        flexWrap: "wrap",
                        justifyContent: "space-between"
                    }}>
                        {results.map((item) => {
                            const coverUrl = item.cover?.image_id
                                ? {uri: `https://images.igdb.com/igdb/image/upload/t_cover_big/${item.cover.image_id}.jpg`}
                                : idleImage;
                            return (
                                <TouchableOpacity
                                    key={item.id}
                                    style={{
                                        width: "48%",
                                        marginBottom: 15,
                                        borderRadius: 8,
                                        overflow: "hidden",
                                        backgroundColor: colors.card,
                                        borderWidth: 1,
                                        borderColor: colors.border
                                    }}
                                    onPress={() => router.push({pathname: '/game-details', params: {gameId: item.id}})}
                                >
                                    <Image source={coverUrl} resizeMode="cover" style={{width: "100%", height: 200}}/>
                                    <View style={{padding: 10}}>
                                        <Text style={{fontSize: 14, fontWeight: "600", color: colors.text}}
                                              numberOfLines={2}>
                                            {item.name}
                                        </Text>
                                    </View>
                                </TouchableOpacity>
                            );
                        })}
                    </View>
                </ScrollView>
            )}
        </SafeAreaProvider>
    );
}