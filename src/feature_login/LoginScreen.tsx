import React, {useState} from "react";
import {ActivityIndicator, ScrollView, Text, TextInput, TouchableOpacity, View} from "react-native";
import Ionicons from "@expo/vector-icons/Ionicons";
import {useTranslation} from "react-i18next";
import {router} from "expo-router";
import {useTheme} from "@/src/shared/contexts/ThemeContext";
import {useAuth} from "@/src/shared/contexts/AuthContext";
import {AuthFailure} from "@/src/shared/data/auth/AuthRepository";

type LoginMode = "signIn" | "signUp";

export function LoginScreen() {
    const {colors} = useTheme();
    const {t} = useTranslation();
    const {signInWithEmail, signUpWithEmail, signInWithGoogle, sendPasswordResetEmail} = useAuth();

    const [mode, setMode] = useState<LoginMode>("signIn");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isPasswordVisible, setIsPasswordVisible] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [isGoogleSubmitting, setIsGoogleSubmitting] = useState(false);
    const [feedback, setFeedback] = useState<{ isError: boolean; message: string } | null>(null);

    const isSigningUp = mode === "signUp";
    const isBusy = isSubmitting || isGoogleSubmitting;

    const getFailureMessage = (error: unknown): string => {
        const reason = error instanceof AuthFailure ? error.reason : "unknown";
        return t(`authFailure_${reason}`);
    };

    const isCancelled = (error: unknown): boolean => {
        return error instanceof AuthFailure && error.reason === "cancelled";
    };

    const leaveLoginScreen = () => {
        if (router.canGoBack()) router.back();
        else router.replace("/");
    };

    const handleSubmit = async () => {
        if (isBusy) return;

        setFeedback(null);
        setIsSubmitting(true);
        try {
            if (isSigningUp) await signUpWithEmail(email, password);
            else await signInWithEmail(email, password);

            leaveLoginScreen();
        } catch (error) {
            if (isCancelled(error)) return;
            setFeedback({isError: true, message: getFailureMessage(error)});
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleGoogleSignIn = async () => {
        if (isBusy) return;

        setFeedback(null);
        setIsGoogleSubmitting(true);
        try {
            await signInWithGoogle();
            leaveLoginScreen();
        } catch (error) {
            if (isCancelled(error)) return;
            setFeedback({isError: true, message: getFailureMessage(error)});
        } finally {
            setIsGoogleSubmitting(false);
        }
    };

    const handleForgotPassword = async () => {
        if (isBusy) return;

        if (!email.trim()) {
            setFeedback({isError: true, message: t("enterEmailForReset")});
            return;
        }

        setFeedback(null);
        setIsSubmitting(true);
        try {
            await sendPasswordResetEmail(email);
            setFeedback({isError: false, message: t("passwordResetEmailSent")});
        } catch (error) {
            setFeedback({isError: true, message: getFailureMessage(error)});
        } finally {
            setIsSubmitting(false);
        }
    };

    const switchMode = () => {
        setMode(currentMode => currentMode === "signIn" ? "signUp" : "signIn");
        setFeedback(null);
    };

    return (
            <ScrollView
                style={{flex: 1, backgroundColor: colors.background}}
                contentContainerStyle={{flexGrow: 1, paddingHorizontal: 24, paddingTop: 24, paddingBottom: 36}}
                keyboardShouldPersistTaps="handled"
            >

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
                    <Text style={{fontSize: 28, fontWeight: "700", color: colors.text}}>{isSigningUp ? t("signUpTitle") : t("welcomeBack")}</Text>
                    <Text style={{
                        fontSize: 15,
                        color: colors.secondaryText,
                        marginTop: 8,
                        textAlign: "center"
                    }}>{isSigningUp ? t("signUpDescription") : t("loginDescription")}</Text>
                </View>

                {feedback && (
                    <View style={{
                        flexDirection: "row",
                        alignItems: "center",
                        gap: 8,
                        borderWidth: 1,
                        borderColor: feedback.isError ? "#EF4444" : "#22C55E",
                        borderRadius: 12,
                        paddingHorizontal: 12,
                        paddingVertical: 10,
                        marginBottom: 20
                    }}>
                        <Ionicons name={feedback.isError ? "alert-circle-outline" : "checkmark-circle-outline"}
                                  size={18} color={feedback.isError ? "#EF4444" : "#22C55E"}/>
                        <Text style={{flex: 1, fontSize: 13, color: colors.text}}>{feedback.message}</Text>
                    </View>
                )}

                <Text style={{fontSize: 14, fontWeight: "600", color: colors.text, marginBottom: 8}}>{t("email")}</Text>
                <TextInput
                    value={email}
                    onChangeText={setEmail}
                    editable={!isBusy}
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
                    {!isSigningUp && (
                        <TouchableOpacity accessibilityRole="button" disabled={isBusy} onPress={handleForgotPassword}>
                            <Text style={{fontSize: 13, fontWeight: "600", color: colors.tint}}>{t("forgotPassword")}</Text>
                        </TouchableOpacity>
                    )}
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
                        value={password}
                        onChangeText={setPassword}
                        editable={!isBusy}
                        placeholder={t("passwordPlaceholder")}
                        placeholderTextColor={colors.secondaryText}
                        secureTextEntry={!isPasswordVisible}
                        autoCapitalize="none"
                        autoComplete={isSigningUp ? "new-password" : "current-password"}
                        style={{flex: 1, color: colors.text}}
                    />
                    <TouchableOpacity onPress={() => setIsPasswordVisible(value => !value)} accessibilityRole="button"
                                      accessibilityLabel={isPasswordVisible ? t("hidePassword") : t("showPassword")}>
                        <Ionicons name={isPasswordVisible ? "eye-off-outline" : "eye-outline"} size={21}
                                  color={colors.secondaryText}/>
                    </TouchableOpacity>
                </View>

                <TouchableOpacity
                    accessibilityRole="button"
                    disabled={isBusy}
                    onPress={handleSubmit}
                    style={{
                        height: 54,
                        borderRadius: 12,
                        backgroundColor: colors.tint,
                        alignItems: "center",
                        justifyContent: "center",
                        marginTop: 28,
                        opacity: isBusy ? 0.6 : 1
                    }}
                >
                    {isSubmitting
                        ? <ActivityIndicator color="#FFFFFF"/>
                        : <Text style={{fontSize: 16, fontWeight: "700", color: "#FFFFFF"}}>{isSigningUp ? t("createAccount") : t("signIn")}</Text>}
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
                    disabled={isBusy}
                    onPress={handleGoogleSignIn}
                    style={{
                        height: 52,
                        borderRadius: 12,
                        borderWidth: 1,
                        borderColor: colors.border,
                        backgroundColor: colors.card,
                        flexDirection: "row",
                        alignItems: "center",
                        justifyContent: "center",
                        gap: 10,
                        opacity: isBusy ? 0.6 : 1
                    }}
                >
                    {isGoogleSubmitting
                        ? <ActivityIndicator color={colors.text}/>
                        : <>
                            <Ionicons name="logo-google" size={19} color={colors.text}/>
                            <Text style={{fontSize: 15, fontWeight: "600", color: colors.text}}>{t("continueWithGoogle")}</Text>
                        </>}
                </TouchableOpacity>

                <View style={{flexDirection: "row", justifyContent: "center", marginTop: 28}}>
                    <Text style={{fontSize: 14, color: colors.secondaryText}}>{isSigningUp ? t("alreadyHaveAccount") : t("noAccount")} </Text>
                    <TouchableOpacity accessibilityRole="button" disabled={isBusy} onPress={switchMode}>
                        <Text style={{fontSize: 14, color: colors.tint, fontWeight: "700"}}>{isSigningUp ? t("signIn") : t("createAccount")}</Text>
                    </TouchableOpacity>
                </View>
            </ScrollView>
    );
}
