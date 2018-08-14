import React from 'react';
import { FlatList, ActivityIndicator, Text, View,Image,Linking,TouchableOpacity  } from 'react-native';
import Input from './list'
import {Inputus} from './list2'
import {Myimage} from './importimage'
import {Myimage2} from './importimage2'

export default class FetchExample extends React.Component {

  state={country:''}

  my =()=> this.setState({country:'my'})

  us =()=> this.setState({country:'us'})


  render()
  {
    const button = <View style={{flexDirection:"row"}}>
   
    <TouchableOpacity  onPress={this.my}>
              <Text>Malaysia</Text>
                </TouchableOpacity>
              <Text>|</Text>
              <TouchableOpacity  onPress={this.us}>
              <Text>US</Text>
              </TouchableOpacity>
      </View>

      let view = [];

      if(this.state.country === 'my'){
        view =  <View style={{flex:1}}><Myimage/><Input/></View>
      }else if(this.state.country === 'us'){
        view= <View style={{flex:1}}><Myimage2/><Inputus/></View>
      }

      return <View style={{flex:1,margin:50}}>
      
      {button}
      {view}
      </View>
  

  }
   

 
}
