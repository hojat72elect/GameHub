import type {BottomTabBarButtonProps} from 'expo-router/js-tabs';
import {PlatformPressable} from 'expo-router/react-navigation';

export function PressableTab(props: BottomTabBarButtonProps) {
    return (
        <PlatformPressable
            {...props}
            android_ripple={{ color: 'transparent' }}
            onPressIn={(ev) => {
                props.onPressIn?.(ev);
            }}
        />
    );
}
