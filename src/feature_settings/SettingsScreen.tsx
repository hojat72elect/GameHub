import {Linking, Modal, Pressable, ScrollView, Text, TouchableOpacity, View} from "react-native";
import React, {useEffect, useState} from "react";
import Animated, {useAnimatedStyle, useSharedValue, withSequence, withTiming} from "react-native-reanimated";
import {useTranslation} from "react-i18next";
import {ThemeMode, useTheme} from "@/src/ThemeContext";
import {useLanguage} from "@/src/LanguageContext";
import Ionicons from "@expo/vector-icons/Ionicons";
import {Language} from "@/src/shared/Language";

export function SettingsScreen() {
    const {t} = useTranslation();
    const {themeMode, colors, setThemeMode} = useTheme();
    const {language, setLanguage} = useLanguage();
    const [isThemeModalVisible, setIsThemeModalVisible] = useState(false);
    const [isLanguageModalVisible, setIsLanguageModalVisible] = useState(false);
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

    const getThemeModeLabel = (mode: ThemeMode) => {
        switch (mode) {
            case "light":
                return t('light');
            case "dark":
                return t('dark');
            case "system":
                return t('systemDefault');
        }
    };

    const getLanguageLabel = (lang: Language) => {
        switch (lang) {
            case "en":
                return t('english');
            case "fa":
                return t('persian');
            case "system":
                return t('systemDefault');
        }
    };

    const handleSelectTheme = (mode: ThemeMode) => {
        setThemeMode(mode);
        setIsThemeModalVisible(false);
    };

    const handleSelectLanguage = (lang: Language) => {
        setLanguage(lang);
        setIsLanguageModalVisible(false);
    };

    return (
        <ScrollView style={{backgroundColor: colors.background}}
                    contentContainerStyle={{marginTop: 36}}>
            <Text style={{
                fontSize: 24, fontWeight: "400", color: colors.text, marginStart: 18
            }}>{t('settingsTitle')}</Text>

            <View style={{
                marginTop: 14,
                backgroundColor: colors.card,
                paddingTop: 14,
                paddingStart: 14,
                paddingBottom: 14,
                borderWidth: 1,
                borderColor: colors.border,
                elevation: 2
            }}>
                <Text style={{color: "#FF4B7D", fontWeight: "semibold", fontSize: 16}}>{t('appearance')}</Text>
                <TouchableOpacity style={{marginTop: 8}} onPress={() => setIsThemeModalVisible(true)}>
                    <Text style={{fontSize: 20, fontWeight: "semibold", color: colors.text}}>{t('theme')}</Text>
                    <Text style={{fontSize: 14, color: colors.secondaryText}}>{getThemeModeLabel(themeMode)}</Text>
                </TouchableOpacity>
                <TouchableOpacity style={{marginTop: 8}} onPress={() => setIsLanguageModalVisible(true)}>
                    <Text style={{fontSize: 20, fontWeight: "semibold", color: colors.text}}>{t('language')}</Text>
                    <Text style={{fontSize: 14, color: colors.secondaryText}}>{getLanguageLabel(language)}</Text>
                </TouchableOpacity>
            </View>

            <View style={{
                marginTop: 14,
                backgroundColor: colors.card,
                paddingTop: 14,
                paddingStart: 14,
                paddingBottom: 14,
                borderWidth: 1,
                borderColor: colors.border,
                elevation: 2
            }}>
                <Text style={{color: "#FF4B7D", fontWeight: "semibold", fontSize: 16}}>{t('about')}</Text>
                <TouchableOpacity
                    style={{paddingTop: 10}}
                    onPress={() => Linking.openURL("https://github.com/hojat72elect/GameHub")}
                >
                    <Text style={{
                        fontSize: 20,
                        fontWeight: "semibold",
                        color: colors.text
                    }}>{t('sourceCode')}</Text>
                    <Text
                        style={{fontSize: 14, color: colors.secondaryText}}>{t('sourceCodeDescription')}</Text>
                </TouchableOpacity>
                <View style={{paddingTop: 10}}>
                    <Text style={{
                        fontSize: 20,
                        fontWeight: "semibold",
                        color: colors.text
                    }}>{t('version')}</Text>
                    <Text style={{fontSize: 14, color: colors.secondaryText}}>v0.0.1-debug</Text>
                </View>
            </View>

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

            <Modal
                animationType="fade"
                transparent={true}
                visible={isThemeModalVisible}
                onRequestClose={() => setIsThemeModalVisible(false)}
            >
                <Pressable style={{
                    flex: 1,
                    backgroundColor: "rgba(0, 0, 0, 0.5)",
                    justifyContent: "center",
                    alignItems: "center",
                    padding: 20
                }} onPress={() => setIsThemeModalVisible(false)}>
                    <View style={{
                        width: "100%",
                        maxWidth: 340,
                        borderRadius: 14,
                        borderWidth: 1,
                        padding: 20,
                        elevation: 5,
                        shadowColor: "#000",
                        shadowOffset: {width: 0, height: 2},
                        shadowOpacity: 0.25,
                        shadowRadius: 4,
                        backgroundColor: colors.card,
                        borderColor: colors.border
                    }}>
                        <Text style={{
                            fontSize: 20,
                            fontWeight: "bold",
                            marginBottom: 20,
                            textAlign: "left",
                            color: colors.text
                        }}>{t('theme')}</Text>

                        <TouchableOpacity
                            style={[
                                {
                                    flexDirection: "row",
                                    alignItems: "center",
                                    justifyContent: "space-between",
                                    paddingVertical: 12,
                                    paddingHorizontal: 16,
                                    borderRadius: 10,
                                    marginBottom: 10,
                                },
                                themeMode === "light" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectTheme("light")}
                        >
                            <View style={{
                                flexDirection: "row",
                                alignItems: "center"
                            }}>
                                <Ionicons name="sunny-outline" size={24} color={colors.text} style={{marginRight: 12}}/>
                                <Text style={{
                                    fontSize: 16,
                                    fontWeight: "500",
                                    color: colors.text,
                                }}>{t('light')}</Text>
                            </View>
                            {themeMode === "light" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>

                        <TouchableOpacity
                            style={[
                                {
                                    flexDirection: "row",
                                    alignItems: "center",
                                    justifyContent: "space-between",
                                    paddingVertical: 12,
                                    paddingHorizontal: 16,
                                    borderRadius: 10,
                                    marginBottom: 10
                                },
                                themeMode === "dark" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectTheme("dark")}
                        >
                            <View style={{
                                flexDirection: "row",
                                alignItems: "center"
                            }}>
                                <Ionicons name="moon-outline" size={24} color={colors.text} style={{marginRight: 12}}/>
                                <Text style={[{
                                    fontSize: 16,
                                    fontWeight: "500"
                                }, {color: colors.text}]}>{t('dark')}</Text>
                            </View>
                            {themeMode === "dark" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>

                        <TouchableOpacity
                            style={[
                                {
                                    flexDirection: "row",
                                    alignItems: "center",
                                    justifyContent: "space-between",
                                    paddingVertical: 12,
                                    paddingHorizontal: 16,
                                    borderRadius: 10,
                                    marginBottom: 10,
                                },
                                themeMode === "system" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectTheme("system")}
                        >
                            <View style={{
                                flexDirection: "row",
                                alignItems: "center"
                            }}>
                                <Ionicons name="settings-outline" size={24} color={colors.text}
                                          style={{marginRight: 12}}/>
                                <Text style={{
                                    fontSize: 16,
                                    fontWeight: "500",
                                    color: colors.text
                                }}>{t('systemDefault')}</Text>
                            </View>
                            {themeMode === "system" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>
                    </View>
                </Pressable>
            </Modal>

            <Modal
                animationType="fade"
                transparent={true}
                visible={isLanguageModalVisible}
                onRequestClose={() => setIsLanguageModalVisible(false)}
            >
                <Pressable style={{
                    flex: 1,
                    backgroundColor: "rgba(0, 0, 0, 0.5)",
                    justifyContent: "center",
                    alignItems: "center",
                    padding: 20
                }} onPress={() => setIsLanguageModalVisible(false)}>
                    <View style={{
                        width: "100%",
                        maxWidth: 340,
                        borderRadius: 14,
                        borderWidth: 1,
                        padding: 20,
                        elevation: 5,
                        shadowColor: "#000",
                        shadowOffset: {width: 0, height: 2},
                        shadowOpacity: 0.25,
                        shadowRadius: 4,
                        backgroundColor: colors.card,
                        borderColor: colors.border
                    }}>
                        <Text style={{
                            fontSize: 20,
                            fontWeight: "bold",
                            marginBottom: 20,
                            textAlign: "left",
                            color: colors.text
                        }}>{t('language')}</Text>

                        <TouchableOpacity
                            style={[
                                {
                                    flexDirection: "row",
                                    alignItems: "center",
                                    justifyContent: "space-between",
                                    paddingVertical: 12,
                                    paddingHorizontal: 16,
                                    borderRadius: 10,
                                    marginBottom: 10,
                                },
                                language === "fa" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectLanguage("fa")}
                        >
                            <View style={{
                                flexDirection: "row",
                                alignItems: "center"
                            }}>
                                <Ionicons name="language-outline" size={24} color={colors.text}
                                          style={{marginRight: 12}}/>
                                <Text style={{
                                    fontSize: 16,
                                    fontWeight: "500",
                                    color: colors.text,
                                }}>{t('persian')}</Text>
                            </View>
                            {language === "fa" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>

                        <TouchableOpacity
                            style={[
                                {
                                    flexDirection: "row",
                                    alignItems: "center",
                                    justifyContent: "space-between",
                                    paddingVertical: 12,
                                    paddingHorizontal: 16,
                                    borderRadius: 10,
                                    marginBottom: 10
                                },
                                language === "en" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectLanguage("en")}
                        >
                            <View style={{
                                flexDirection: "row",
                                alignItems: "center"
                            }}>
                                <Ionicons name="globe-outline" size={24} color={colors.text} style={{marginRight: 12}}/>
                                <Text style={[{
                                    fontSize: 16,
                                    fontWeight: "500"
                                }, {color: colors.text}]}>{t('english')}</Text>
                            </View>
                            {language === "en" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>

                        <TouchableOpacity
                            style={[
                                {
                                    flexDirection: "row",
                                    alignItems: "center",
                                    justifyContent: "space-between",
                                    paddingVertical: 12,
                                    paddingHorizontal: 16,
                                    borderRadius: 10,
                                    marginBottom: 10,
                                },
                                language === "system" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectLanguage("system")}
                        >
                            <View style={{
                                flexDirection: "row",
                                alignItems: "center"
                            }}>
                                <Ionicons name="settings-outline" size={24} color={colors.text}
                                          style={{marginRight: 12}}/>
                                <Text style={{
                                    fontSize: 16,
                                    fontWeight: "500",
                                    color: colors.text
                                }}>{t('systemDefault')}</Text>
                            </View>
                            {language === "system" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>
                    </View>
                </Pressable>
            </Modal>
        </ScrollView>
    );
}