import {Link} from 'expo-router';

import {ThemedText} from '@/components/themed-text';
import {ThemedView} from '@/components/themed-view';

export default function ModalScreen() {
    return (
        <ThemedView style={{
            flex: 1,
            alignItems: 'center',
            justifyContent: 'center',
            padding: 20,
        }}>
            <ThemedText type="title">This is a modal</ThemedText>
            <Link href="/" dismissTo style={{
                marginTop: 15,
                paddingVertical: 15,
            }}>
                <ThemedText type="link">Go to home screen</ThemedText>
            </Link>
        </ThemedView>
    );
}
