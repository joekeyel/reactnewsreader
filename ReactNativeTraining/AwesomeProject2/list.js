
import React from 'react';
import { FlatList, ActivityIndicator, Text, View,Image,Linking,TouchableOpacity  } from 'react-native';


export default class Input extends React.Component{
    
    constructor(props){
        super(props);
        this.state ={ isLoading: true}
      }
    
      componentDidMount(){
        return fetch('https://newsapi.org/v2/top-headlines?country=my&apiKey=456f81eb6809423c9edafd3b1f7e85ad')
          .then((response) => response.json())
          .then((responseJson) => {
    
            this.setState({
              isLoading: false,
              dataSource: responseJson.articles,
            }, function(){
    
            });
    
          })
          .catch((error) =>{
            console.error(error);
          });
        }
   render(){

    if(this.state.isLoading){
      return(
        <View style={{flex: 1, padding: 20}}>
          <ActivityIndicator/>
        </View>
      )
    }

    return(
      <View style={{flex: 1, paddingTop:20}}>
      <View style={{flexDirection:"row"}}>
      <Text style={{fontSize:20,alignContent:"center",alignItems:"center",flex:9}}>Today Malaysia Headline</Text>
      
      <TouchableOpacity onPress={this.up} style={{flex:1}}>
        <Text style={{fontSize:11}}>Reload</Text>
      </TouchableOpacity>
      </View>

        <FlatList style={{padding:10,margin:10}}
          data={this.state.dataSource}
          renderItem={({item}) => 
          <View>
          <Text>{item.title}</Text>
        
          <Text style={{color: 'blue'}}
          onPress={() => Linking.openURL(item.url)}>
           Read this news .....
        </Text>
          {item.urlToImage != null && <Image source={{uri:item.urlToImage}} style={{width:100,height:100}} resizeMode='contain'/>}

        </View>
        
        }
          keyExtractor={(item, index) => index}
        />
      </View>
    );
  }
    
}