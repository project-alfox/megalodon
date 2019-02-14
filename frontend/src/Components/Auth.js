
import React, { Component } from 'react';


export default class AuthProvider extends Component {
    constructor(props) {
      super(props);
    }

    

    state = {

    }
  
    render() {
      return (
        <Provider value={this.state.value}>
          <Toolbar />
        </Provider>
      );
    }
}

