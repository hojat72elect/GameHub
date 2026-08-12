import React from "react";
import {Text, TouchableOpacity, ViewStyle} from "react-native";
import {useTheme} from "@/src/shared/states/ThemeContext";

export function InteractableSettingsSection(
    {
        title,
        subtitle,
        onPress
    }: {
        title: string;
        subtitle: string;
        onPress: () => void;
        style?: ViewStyle
    }) {
    const {colors} = useTheme();

    return (
        <TouchableOpacity style={{marginTop: 8}} onPress={onPress}>
            <Text style={{fontSize: 20, fontWeight: "semibold", color: colors.text}}>{title}</Text>
            <Text style={{fontSize: 14, color: colors.secondaryText}}>{subtitle}</Text>
        </TouchableOpacity>
    );
}
