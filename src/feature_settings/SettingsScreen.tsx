import {Linking, Modal, Pressable, ScrollView, Text, TouchableOpacity, View} from "react-native";
import React, {useState} from "react";
import {ThemeMode, useTheme} from "@/src/ThemeContext";
import Ionicons from "@expo/vector-icons/Ionicons";

export function SettingsScreen() {
    const {themeMode, colors, setThemeMode} = useTheme();
    const [modalVisible, setModalVisible] = useState(false);

    const getThemeModeLabel = (mode: ThemeMode) => {
        switch (mode) {
            case "light":
                return "Light";
            case "dark":
                return "Dark";
            case "system":
                return "System Default";
        }
    };

    const handleSelectTheme = (mode: ThemeMode) => {
        setThemeMode(mode);
        setModalVisible(false);
    };

    return (
        <ScrollView style={{backgroundColor: colors.background}}
                    contentContainerStyle={{marginTop: 36}}>
            <Text style={{
                fontSize: 24, fontWeight: "400", color: colors.text, marginStart:18
            }}>Settings</Text>

            <View style={{
                marginTop:14,
                backgroundColor: colors.card,
                paddingTop: 14,
                paddingStart:14,
                paddingBottom:14,
                borderWidth: 1,
                borderColor: colors.border,
                elevation: 2
            }}>
                <Text style={{color: "#FF4B7D", fontWeight: "semibold", fontSize: 16}}>Appearance</Text>
                <TouchableOpacity style={{marginTop: 8}} onPress={() => setModalVisible(true)}>
                    <Text style={{fontSize: 20, fontWeight: "semibold", color: colors.text}}>Theme</Text>
                    <Text
                        style={{fontSize: 14, color: colors.secondaryText}}>{getThemeModeLabel(themeMode)}</Text>
                </TouchableOpacity>
            </View>

            <View style={{  marginTop:14,
                backgroundColor: colors.card,
                paddingTop: 14,
                paddingStart:14,
                paddingBottom:14,
                borderWidth: 1,
                borderColor: colors.border,
                elevation: 2}}>
                <Text style={{color: "#FF4B7D", fontWeight: "semibold", fontSize: 16}}>About</Text>
                <TouchableOpacity
                    style={{paddingTop:10}}
                    onPress={() => Linking.openURL("https://github.com/hojat72elect/GameHub")}
                >
                    <Text style={{fontSize: 20, fontWeight: "semibold", color: colors.text}}>Source Code</Text>
                    <Text style={{fontSize: 14, color: colors.secondaryText}}>View the source code of the app</Text>
                </TouchableOpacity>
                <View style={{paddingTop:10}}>
                    <Text style={{fontSize: 20, fontWeight: "semibold", color: colors.text}}>Version</Text>
                    <Text style={{fontSize: 14, color: colors.secondaryText}}>v0.0.1-debug</Text>
                </View>
            </View>

            <Modal
                animationType="fade"
                transparent={true}
                visible={modalVisible}
                onRequestClose={() => setModalVisible(false)}
            >
                <Pressable style={{}} onPress={() => setModalVisible(false)}>
                    <View style={{}}>
                        <Text style={{}}>Theme</Text>

                        <TouchableOpacity
                            style={{}}
                            onPress={() => handleSelectTheme("light")}
                        >
                            <View style={{}}>
                                <Ionicons name="sunny-outline" size={24} color={colors.text} style={{}}/>
                                <Text style={{}}>Light</Text>
                            </View>
                            {themeMode === "light" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>

                        <TouchableOpacity
                            style={{}}
                            onPress={() => handleSelectTheme("dark")}
                        >
                            <View style={{}}>
                                <Ionicons name="moon-outline" size={24} color={colors.text} style={{}}/>
                                <Text style={{}}>Dark</Text>
                            </View>
                            {themeMode === "dark" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>

                        <TouchableOpacity
                            style={{}}
                            onPress={() => handleSelectTheme("system")}
                        >
                            <View style={{}}>
                                <Ionicons name="settings-outline" size={24} color={colors.text}
                                          style={{}}/>
                                <Text style={{}}>System Default</Text>
                            </View>
                            {themeMode === "system" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>
                    </View>
                </Pressable>
            </Modal>
        </ScrollView>
    );
}
