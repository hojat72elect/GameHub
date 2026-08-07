import {Tabs} from 'expo-router';
import React from 'react';
import {useTranslation} from 'react-i18next';

import SettingsIcon from '@/assets/svg/cog_outline.svg';
import DiscoverIcon from '@/assets/svg/compass_rose.svg';
import LikesIcon from '@/assets/svg/heart.svg';
import NewsIcon from '@/assets/svg/newspaper.svg';

import {useTheme} from "@/src/ThemeContext";
import {HapticTab} from "@/src/HapticTab";

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
                tabBarButton: HapticTab,
            }}>
            <Tabs.Screen
                name="index"
                options={{
                    title: t('discoverTitle'),
                    // @ts-ignore
                    tabBarIcon: ({color}) => <DiscoverIcon width={28} height={28} style={{color}}/>,
                }}
            />
            <Tabs.Screen
                name="likes"
                options={{
                    title: t('likesTitle'),
                    // @ts-ignore
                    tabBarIcon: ({color}) => <LikesIcon width={28} height={28} style={{color}}/>,
                }}
            />
            <Tabs.Screen
                name="news"
                options={{
                    title: t('newsTitle'),
                    // @ts-ignore
                    tabBarIcon: ({color}) => <NewsIcon width={28} height={28} style={{color}}/>,
                }}
            />
            <Tabs.Screen
                name="settings"
                options={{
                    title: t('settingsTitle'),
                    // @ts-ignore
                    tabBarIcon: ({color}) => <SettingsIcon width={28} height={28} style={{color}}/>,
                }}
            />
        </Tabs>
    );
}
