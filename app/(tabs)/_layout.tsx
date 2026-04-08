import {Tabs} from 'expo-router';
import React from 'react';

import SettingsIcon from '@/assets/svg/cog_outline.svg';
import DiscoverIcon from '@/assets/svg/compass_rose.svg';
import LikesIcon from '@/assets/svg/heart.svg';
import NewsIcon from '@/assets/svg/newspaper.svg';

import {useColorScheme} from "react-native";
import {HapticTab} from "@/src/HapticTab";
import {Colors} from "@/src/Color";

export default function TabLayout() {
    const colorScheme = useColorScheme();

    return (
        <Tabs
            screenOptions={{
                tabBarActiveTintColor: Colors[colorScheme ?? 'light'].tint,
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
