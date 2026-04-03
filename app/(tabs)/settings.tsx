import {ScrollView, StyleSheet, Text, TouchableOpacity, View} from 'react-native';

export default function SettingsScreen() {
    return (
        <ScrollView contentContainerStyle={{padding: 20}}>
            <Text style={{
                fontSize: 28,
                fontFamily: 'System',
                marginBottom: 30,
            }}>Settings</Text>

            <View style={styles.section}>
                <Text style={styles.sectionHeader}>Appearance</Text>
                <TouchableOpacity style={styles.item}>
                    <Text style={styles.itemLabel}>Theme</Text>
                    <Text style={styles.itemValue}>Dark</Text>
                </TouchableOpacity>
            </View>

            <View style={styles.section}>
                <Text style={styles.sectionHeader}>About</Text>
                <TouchableOpacity style={styles.item}>
                    <Text style={styles.itemLabel}>Source Code</Text>
                    <Text style={styles.itemValue}>View the source code of the app</Text>
                </TouchableOpacity>
                <View style={styles.item}>
                    <Text style={styles.itemLabel}>Version</Text>
                    <Text style={styles.itemValue}>v0.0.1-debug</Text>
                </View>
            </View>

        </ScrollView>
    );
}


const styles = StyleSheet.create({
    section: {marginBottom: 25},
    sectionHeader: {fontSize: 18, color: "#000", marginBottom: 10, fontFamily: "System"},
    item: {marginBottom: 15},
    itemLabel: {fontSize: 18, fontWeight: "500"},
    itemValue: {fontSize: 16, marginTop: 2},
});