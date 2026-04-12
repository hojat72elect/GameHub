import {FlatList, Image, Text, TouchableOpacity, View} from 'react-native';
import {SafeAreaProvider} from 'react-native-safe-area-context';
import idleImage from "../../assets/images/game_landscape_placeholder.webp";

export default function NewsScreen() {

    const FAKE_DATA = [
        {
            id: 1,
            title: "It's Not A Switch 2 Mario Galaxy Bundle, But This Deal Is The Next Best Thing",
            description: "You can save $20 on two of the greatest Super Mario games of all time when you buy a Switch 2 console.",
            timestamp: 'Apr 6, 10:13 AM',
        },
        {
            id: 2,
            title: "Star Wars Actor Sam Witwer Says A Darth Maul Game Could Be Something",
            description: "The voice of Maul discusses the potential for a standalone title...",
            timestamp: 'Apr 6, 8:11 AM',
        },
        {
            id: 3,
            title: "Fanatical's New Wholesome Collection Includes Up To 18 Cozy PC Games",
            description: "Fill out your collection of quaint titles with games like Henry Halfhead, Dogpile, and so much more.",
            timestamp: 'Apr 6, 6:58 AM',
        },
    ];

    const NewsCard = (item: { id: number; title: string; description: string; timestamp: string }) => (
        <TouchableOpacity activeOpacity={0.9} style={{backgroundColor: "#FFF", marginBottom: 25, overflow: "hidden"}}>
            <Image source={idleImage} resizeMode="cover" style={{width: "100%", height: 200}}/>
            <View style={{padding: 15}}>
                <Text style={{
                    fontSize: 20,
                    fontWeight: "600",
                    color: "#1A1A1A",
                    lineHeight: 26,
                    marginBottom: 8
                }}>{item.title}</Text>
                <Text numberOfLines={3}
                      style={{fontSize: 16, color: "#444", lineHeight: 22, marginBottom: 10}}
                >{item.description}</Text>
                <View>
                    <Text style={{fontSize: 14, color: "#888"}}>🕒 {item.timestamp}</Text>
                </View>
            </View>
        </TouchableOpacity>
    );


    return (
        <SafeAreaProvider style={{flex: 1, backgroundColor: "#FFF"}}>
            <View style={{
                paddingTop: 35,
                paddingBottom: 10,
                paddingHorizontal: 20,
                borderBottomWidth: 1,
                borderBottomColor: "#F0F0F0"
            }}>
                <Text style={{fontSize: 28, fontWeight: "300", fontFamily: "System", color: "#000"}}>News</Text>
            </View>

            <FlatList
                data={FAKE_DATA}
                renderItem={({item}) => <NewsCard {...item}/>}
                keyExtractor={(item) => String(item.id)}
                showsVerticalScrollIndicator={true}
                contentContainerStyle={{paddingBottom: 100}}
            />
        </SafeAreaProvider>
    );
}
