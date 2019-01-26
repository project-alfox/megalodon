import React, { Component } from 'react';
import User from '../API/login'

import './Login.scss';

class Login extends Component {

  constructor() {
    super()
    this.state = {
      username: '',
      player: null
    }
  }

  componentDidMount() {
    User.status()
      .then(user => {
        this.setState({player: user})
      });
  }

  login() {
    User.loginAs(this.state.username)
    .then(result => {
      this.setState({player: result})
      if(this.props.onLogin) {
        this.props.onLogin(result);
      }
    })
  }

  render() {
    if(this.state.player == null) {
      return (
        <div className="login-container">
          <label>Login</label>
          <input type="text" value={this.state.username} onChange={e => this.setState({username: e.target.value})} />
          <label>Password</label>
          <input type="text" value="not used" onChange={e => true} />
          <button onClick={e => this.login()}>Login</button>
        </div>
      );
    } else {
      return (
        <div className="login-container">
          <button onClick={event => {
            this.setState({player: null})
            if(this.props.onLogin) {
              this.props.onLogin(null);
            }
          }}>Logout</button>
        </div>
      )
    }
  }
}

export default Login;
