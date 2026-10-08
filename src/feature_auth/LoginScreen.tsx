import React, {useState} from "react";
import {KeyboardAvoidingView, Platform, ScrollView, Text, TextInput, TouchableOpacity, View} from "react-native";
import {router} from "expo-router";
import Ionicons from "@expo/vector-icons/Ionicons";
import {useTranslation} from "react-i18next";
import {useTheme} from "@/src/shared/contexts/ThemeContext";

export function LoginScreen() {
    const {colors} = useTheme();
    const {t} = useTranslation();
    const [passwordVisible, setPasswordVisible] = useState(false);

    return (
        <KeyboardAvoidingView
            style={{flex: 1, backgroundColor: colors.background}}
            behavior={Platform.OS === "ios" ? "padding" : undefined}
        >
            <ScrollView
                contentContainerStyle={{flexGrow: 1, paddingHorizontal: 24, paddingTop: 24, paddingBottom: 36}}
                keyboardShouldPersistTaps="handled"
            >
                <TouchableOpacity
                    onPress={() => router.back()}
                    accessibilityRole="button"
                    accessibilityLabel={t("back")}
                    style={{
                        width: 44,
                        height: 44,
                        borderRadius: 22,
                        borderWidth: 1,
                        borderColor: colors.border,
                        alignItems: "center",
                        justifyContent: "center"
                    }}
                >
                    <Ionicons name="arrow-back" size={22} color={colors.text}/>
                </TouchableOpacity>

                <View style={{alignItems: "center", marginTop: 44, marginBottom: 34}}>
                    <View style={{
                        width: 68,
                        height: 68,
                        borderRadius: 22,
                        backgroundColor: colors.tint,
                        alignItems: "center",
                        justifyContent: "center",
                        marginBottom: 20
                    }}>
                        <Ionicons name="game-controller" size={36} color="#FFFFFF"/>
                    </View>
                    <Text style={{fontSize: 28, fontWeight: "700", color: colors.text}}>{t("welcomeBack")}</Text>
                    <Text style={{
                        fontSize: 15,
                        color: colors.secondaryText,
                        marginTop: 8,
                        textAlign: "center"
                    }}>{t("loginDescription")}</Text>
                </View>

                <Text style={{fontSize: 14, fontWeight: "600", color: colors.text, marginBottom: 8}}>{t("email")}</Text>
                <TextInput
                    placeholder={t("emailPlaceholder")}
                    placeholderTextColor={colors.secondaryText}
                    keyboardType="email-address"
                    autoCapitalize="none"
                    autoComplete="email"
                    style={{
                        height: 54,
                        borderWidth: 1,
                        borderColor: colors.border,
                        borderRadius: 12,
                        paddingHorizontal: 16,
                        color: colors.text,
                        backgroundColor: colors.card,
                        marginBottom: 20
                    }}
                />

                <View style={{
                    flexDirection: "row",
                    justifyContent: "space-between",
                    alignItems: "center",
                    marginBottom: 8
                }}>
                    <Text style={{fontSize: 14, fontWeight: "600", color: colors.text}}>{t("password")}</Text>
                    <TouchableOpacity accessibilityRole="button">
                        <Text style={{fontSize: 13, fontWeight: "600", color: colors.tint}}>{t("forgotPassword")}</Text>
                    </TouchableOpacity>
                </View>
                <View style={{
                    height: 54,
                    borderWidth: 1,
                    borderColor: colors.border,
                    borderRadius: 12,
                    paddingLeft: 16,
                    paddingRight: 12,
                    flexDirection: "row",
                    alignItems: "center",
                    backgroundColor: colors.card
                }}>
                    <TextInput
                        placeholder={t("passwordPlaceholder")}
                        placeholderTextColor={colors.secondaryText}
                        secureTextEntry={!passwordVisible}
                        autoComplete="password"
                        style={{flex: 1, color: colors.text}}
                    />
                    <TouchableOpacity onPress={() => setPasswordVisible(value => !value)} accessibilityRole="button"
                                      accessibilityLabel={passwordVisible ? t("hidePassword") : t("showPassword")}>
                        <Ionicons name={passwordVisible ? "eye-off-outline" : "eye-outline"} size={21}
                                  color={colors.secondaryText}/>
                    </TouchableOpacity>
                </View>

                <TouchableOpacity
                    accessibilityRole="button"
                    style={{
                        height: 54,
                        borderRadius: 12,
                        backgroundColor: colors.tint,
                        alignItems: "center",
                        justifyContent: "center",
                        marginTop: 28
                    }}
                >
                    <Text style={{fontSize: 16, fontWeight: "700", color: "#FFFFFF"}}>{t("signIn")}</Text>
                </TouchableOpacity>

                <View style={{flexDirection: "row", alignItems: "center", marginVertical: 24}}>
                    <View style={{height: 1, flex: 1, backgroundColor: colors.border}}/>
                    <Text style={{
                        color: colors.secondaryText,
                        fontSize: 13,
                        marginHorizontal: 12
                    }}>{t("orContinueWith")}</Text>
                    <View style={{height: 1, flex: 1, backgroundColor: colors.border}}/>
                </View>

                <TouchableOpacity
                    accessibilityRole="button"
                    style={{
                        height: 52,
                        borderRadius: 12,
                        borderWidth: 1,
                        borderColor: colors.border,
                        backgroundColor: colors.card,
                        flexDirection: "row",
                        alignItems: "center",
                        justifyContent: "center",
                        gap: 10
                    }}
                >
                    <Ionicons name="logo-google" size={19} color={colors.text}/>
                    <Text style={{fontSize: 15, fontWeight: "600", color: colors.text}}>{t("continueWithGoogle")}</Text>
                </TouchableOpacity>

                <View style={{flexDirection: "row", justifyContent: "center", marginTop: 28}}>
                    <Text style={{fontSize: 14, color: colors.secondaryText}}>{t("noAccount")} </Text>
                    <TouchableOpacity accessibilityRole="button">
                        <Text style={{fontSize: 14, color: colors.tint, fontWeight: "700"}}>{t("createAccount")}</Text>
                    </TouchableOpacity>
                </View>
            </ScrollView>
        </KeyboardAvoidingView>
    );
}
