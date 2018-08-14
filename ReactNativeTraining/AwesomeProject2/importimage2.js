import React from 'react';
import { FlatList, ActivityIndicator, Text, View,Image,Linking,TouchableOpacity  } from 'react-native';


const image = require('./idecomlogo.png');

export class Myimage2 extends React.Component{


    render(){


return(
        
       <Image source={image} style={{width:50,height:50}} resizeMode='contain'/>


       )
    }




}