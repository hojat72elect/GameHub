import {Tabs} from 'expo-router/js-tabs';
import React from 'react';
import {View} from 'react-native';
import {useTranslation} from 'react-i18next';
import type {SvgProps} from 'react-native-svg';
import SettingsIcon from '@/assets/svg/cog_outline.svg';
import DiscoverIcon from '@/assets/svg/compass_rose.svg';
import LikesIcon from '@/assets/svg/heart.svg';
import NewsIcon from '@/assets/svg/newspaper.svg';
import {useTheme} from "@/src/shared/contexts/ThemeContext";
import {PressableTab} from "@/src/shared/PressableTab";

function TabBarIcon({
                        Icon,
                        color,
                        focused,
                        activeBackgroundColor,
                    }: {
    Icon: React.FC<SvgProps>;
    color: any;
    focused: boolean;
    activeBackgroundColor: string;
}) {
    return (
        <View
            style={[
                {
                    width: 56,
                    height: 32,
                    borderRadius: 16,
                    justifyContent: 'center',
                    alignItems: 'center'
                },
                focused && {backgroundColor: activeBackgroundColor},
            ]}>
            {/* @ts-ignore */}
            <Icon width={24} height={24} color={color} style={{color: color}}/>
        </View>
    );
}

export default function TabLayout() {
    const {colors} = useTheme();
    const {t} = useTranslation();

    return (
        <Tabs
            screenOptions={{
                tabBarActiveTintColor: '#FF4B7D',
                tabBarInactiveTintColor: colors.icon,
                tabBarStyle: {
                    backgroundColor: colors.background,
                    borderTopColor: colors.border,
                },
                tabBarLabelStyle: {
                    fontSize: 11,
                },
                headerShown: false,
                tabBarButton: PressableTab,
            }}>
            <Tabs.Screen
                name="index"
                options={{
                    title: t('discoverTitle'),
                    tabBarIcon: ({color, focused}) => (
                        <TabBarIcon
                            Icon={DiscoverIcon}
                            color={color}
                            focused={focused}
                            activeBackgroundColor={colors.tabBarActiveIndicator}
                        />
                    ),
                }}
            />
            <Tabs.Screen
                name="likes"
                options={{
                    title: t('likesTitle'),
                    tabBarIcon: ({color, focused}) => (
                        <TabBarIcon
                            Icon={LikesIcon}
                            color={color}
                            focused={focused}
                            activeBackgroundColor={colors.tabBarActiveIndicator}
                        />
                    ),
                }}
            />
            <Tabs.Screen
                name="news"
                options={{
                    title: t('newsTitle'),
                    tabBarIcon: ({color, focused}) => (
                        <TabBarIcon
                            Icon={NewsIcon}
                            color={color}
                            focused={focused}
                            activeBackgroundColor={colors.tabBarActiveIndicator}
                        />
                    ),
                }}
            />
            <Tabs.Screen
                name="settings"
                options={{
                    title: t('settingsTitle'),
                    tabBarIcon: ({color, focused}) => (
                        <TabBarIcon
                            Icon={SettingsIcon}
                            color={color}
                            focused={focused}
                            activeBackgroundColor={colors.tabBarActiveIndicator}
                        />
                    ),
                }}
            />
        </Tabs>
    );
}
