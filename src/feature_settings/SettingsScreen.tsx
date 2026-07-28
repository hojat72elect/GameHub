import {Modal, Pressable, ScrollView, StyleSheet, Text, TouchableOpacity, View} from "react-native";
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
        <ScrollView style={{backgroundColor: colors.background}} contentContainerStyle={{padding: 20, paddingTop: 50}}>
            <Text style={{
                fontSize: 28,
                fontWeight: "bold",
                fontFamily: 'System',
                color: colors.text,
                marginBottom: 30,
            }}>Settings</Text>

            <View style={styles.section}>
                <Text style={[styles.sectionHeader, {color: colors.tint}]}>Appearance</Text>
                <TouchableOpacity style={styles.item} onPress={() => setModalVisible(true)}>
                    <Text style={[styles.itemLabel, {color: colors.text}]}>Theme</Text>
                    <Text
                        style={[styles.itemValue, {color: colors.secondaryText}]}>{getThemeModeLabel(themeMode)}</Text>
                </TouchableOpacity>
            </View>

            <View style={[styles.section, {borderTopWidth: 1, borderTopColor: colors.border, paddingTop: 20}]}>
                <Text style={[styles.sectionHeader, {color: colors.tint}]}>About</Text>
                <TouchableOpacity style={styles.item}>
                    <Text style={[styles.itemLabel, {color: colors.text}]}>Source Code</Text>
                    <Text style={[styles.itemValue, {color: colors.secondaryText}]}>View the source code of the
                        app</Text>
                </TouchableOpacity>
                <View style={styles.item}>
                    <Text style={[styles.itemLabel, {color: colors.text}]}>Version</Text>
                    <Text style={[styles.itemValue, {color: colors.secondaryText}]}>v0.0.1-debug</Text>
                </View>
            </View>

            <Modal
                animationType="fade"
                transparent={true}
                visible={modalVisible}
                onRequestClose={() => setModalVisible(false)}
            >
                <Pressable style={styles.modalOverlay} onPress={() => setModalVisible(false)}>
                    <View style={[styles.modalContent, {backgroundColor: colors.card, borderColor: colors.border}]}>
                        <Text style={[styles.modalTitle, {color: colors.text}]}>Theme</Text>

                        <TouchableOpacity
                            style={[
                                styles.optionItem,
                                themeMode === "light" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectTheme("light")}
                        >
                            <View style={styles.optionLeft}>
                                <Ionicons name="sunny-outline" size={24} color={colors.text} style={styles.optionIcon}/>
                                <Text style={[styles.optionText, {color: colors.text}]}>Light</Text>
                            </View>
                            {themeMode === "light" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>

                        <TouchableOpacity
                            style={[
                                styles.optionItem,
                                themeMode === "dark" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectTheme("dark")}
                        >
                            <View style={styles.optionLeft}>
                                <Ionicons name="moon-outline" size={24} color={colors.text} style={styles.optionIcon}/>
                                <Text style={[styles.optionText, {color: colors.text}]}>Dark</Text>
                            </View>
                            {themeMode === "dark" && (
                                <Ionicons name="checkmark" size={20} color={colors.tint}/>
                            )}
                        </TouchableOpacity>

                        <TouchableOpacity
                            style={[
                                styles.optionItem,
                                themeMode === "system" && {backgroundColor: colors.border}
                            ]}
                            onPress={() => handleSelectTheme("system")}
                        >
                            <View style={styles.optionLeft}>
                                <Ionicons name="settings-outline" size={24} color={colors.text}
                                          style={styles.optionIcon}/>
                                <Text style={[styles.optionText, {color: colors.text}]}>System Default</Text>
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

const styles = StyleSheet.create({
    section: {marginBottom: 25},
    sectionHeader: {fontSize: 18, fontWeight: "600", marginBottom: 15, fontFamily: "System"},
    item: {marginBottom: 15},
    itemLabel: {fontSize: 18, fontWeight: "500"},
    itemValue: {fontSize: 16, marginTop: 2},
    modalOverlay: {
        flex: 1,
        backgroundColor: "rgba(0, 0, 0, 0.5)",
        justifyContent: "center",
        alignItems: "center",
        padding: 20
    },
    modalContent: {
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
    },
    modalTitle: {
        fontSize: 20,
        fontWeight: "bold",
        marginBottom: 20,
        textAlign: "left",
    },
    optionItem: {
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
        paddingVertical: 12,
        paddingHorizontal: 16,
        borderRadius: 10,
        marginBottom: 10,
    },
    optionLeft: {
        flexDirection: "row",
        alignItems: "center",
    },
    optionIcon: {
        marginRight: 12,
    },
    optionText: {
        fontSize: 16,
        fontWeight: "500",
    },
    closeButton: {
        marginTop: 15,
        paddingVertical: 12,
        borderRadius: 10,
        alignItems: "center",
    },
    closeButtonText: {
        color: "#FFF",
        fontSize: 16,
        fontWeight: "bold",
    }
});