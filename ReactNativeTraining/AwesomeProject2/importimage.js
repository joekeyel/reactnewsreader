import React from 'react';
import { FlatList, ActivityIndicator, Text, View,Image,Linking,TouchableOpacity  } from 'react-native';


const image = require('./bg.jpg');

export class Myimage extends React.Component{


    render(){


return(
        
       <Image source={image} style={{width:50,height:50}} resizeMode='contain'/>


       )
    }




}