import {Image, SafeAreaView, ScrollView, Text, TouchableOpacity, View} from 'react-native';
import SearchIcon from '@/assets/svg/magnify.svg';
import idleImage from "../../assets/images/game_portrait_placeholder.webp";

/**
 * Each one of the seemingly identical sections you see in the discover screen.
 * Right now, we have these 4 sections :
 *
 * 1 - Popular
 * 2 - Recently released
 * 3 - Coming Soon
 * 4 - Most anticipated
 */
function DiscoverScreenSection({title, data}: { title: string, data: any[] }) {
    return (<View style={{marginBottom: 25}}>
        <View style={{
            flexDirection: "row",
            justifyContent: "space-between",
            alignItems: "center",
            paddingHorizontal: 15,
            marginBottom: 10
        }}>
            <Text style={{fontSize: 22, fontWeight: "bold", fontFamily: "serif", color: "#333"}}>{title}</Text>
            <TouchableOpacity>
                <Text style={{color: "#FF4B7D", fontWeight: "600", fontSize: 14}}>SEE ALL</Text>
            </TouchableOpacity>
        </View>

        <ScrollView horizontal={true} showsHorizontalScrollIndicator={false} contentContainerStyle={{paddingLeft: 12}}>
            {data.map((item) => (
                <TouchableOpacity key={item.id} style={{
                    marginRight: 12,
                    borderRadius: 8,
                    overflow: "hidden",
                    backgroundColor: "#EEE"
                }}>
                    <Image source={idleImage} style={{width: 110, height: 180, resizeMode: "cover"}}/>
                </TouchableOpacity>
            ))}
        </ScrollView>
    </View>);
}

export default function HomeScreen() {

    const mockData = [
        {id: '1', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '2', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '3', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '3', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '4', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '5', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '6', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '7', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '8', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '9', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '10', imageUrl: 'https://via.placeholder.com/120x180'},
        {id: '11', imageUrl: 'https://via.placeholder.com/120x180'},
    ];

    return (
        <SafeAreaView style={{flex: 1, backgroundColor: "#FFF"}}>
            <View style={{
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
                paddingHorizontal: 20,
                paddingVertical: 15,
                borderBottomWidth: 1,
                borderBottomColor: "#F0F0F0"
            }}>
                <Text style={{fontSize: 28, fontWeight: "bold"}}>Discover</Text>
                <TouchableOpacity>
                    <SearchIcon width={24} height={24} color="black"/>
                </TouchableOpacity>
            </View>

            <ScrollView showsVerticalScrollIndicator={false}>
                <DiscoverScreenSection title="Popular" data={mockData}/>
                <DiscoverScreenSection title="Recently Released" data={mockData}/>
                <DiscoverScreenSection title="Coming Soon" data={mockData}/>
                <DiscoverScreenSection title="Most Anticipated" data={mockData}/>
            </ScrollView>
        </SafeAreaView>
    );
}
