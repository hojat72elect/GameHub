import {Text} from 'react-native';
import React from "react";
import {useTheme} from "@/src/shared/contexts/ThemeContext";

export function ScreenNameLabel({title}: { title: string }): React.JSX.Element {

    const {colors} = useTheme();

    return (<Text style={{fontSize: 24, fontWeight: "400", color: colors.text}}>{title}</Text>);
}