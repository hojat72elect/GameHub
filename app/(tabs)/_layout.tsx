import {Tabs} from 'expo-router';
import React from 'react';

import SettingsIcon from '@/assets/svg/cog_outline.svg';
import DiscoverIcon from '@/assets/svg/compass_rose.svg';
import LikesIcon from '@/assets/svg/heart.svg';
import NewsIcon from '@/assets/svg/newspaper.svg';

import {useTheme} from "@/src/ThemeContext";
import {HapticTab} from "@/src/HapticTab";

export default function TabLayout() {
    const {colors} = useTheme();

    return (
        <Tabs
            screenOptions={{
                tabBarActiveTintColor: colors.tint,
                tabBarInactiveTintColor: colors.icon,
                tabBarStyle: {
                    backgroundColor: colors.background,
                    borderTopColor: colors.border,
                },
                headerShown: false,
                tabBarButton: HapticTab,
            }}>
            <Tabs.Screen
                name="index"
                options={{
                    title: 'Discover',
                    tabBarIcon: ({color}) => <DiscoverIcon width={28} height={28} fill={color}/>,
                }}
            />
            <Tabs.Screen
                name="likes"
                options={{
                    title: 'Likes',
                    tabBarIcon: ({color}) => <LikesIcon width={28} height={28} fill={color}/>,
                }}
            />
            <Tabs.Screen
                name="news"
                options={{
                    title: 'News',
                    tabBarIcon: ({color}) => <NewsIcon width={28} height={28} fill={color}/>,
                }}
            />
            <Tabs.Screen
                name="settings"
                options={{
                    title: 'Settings',
                    tabBarIcon: ({color}) => <SettingsIcon width={28} height={28} fill={color}/>,
                }}
            />
        </Tabs>
    );
}
