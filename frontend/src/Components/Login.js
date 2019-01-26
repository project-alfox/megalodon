import React, { Component } from 'react';
import User from '../API/login'

import './Login.scss';

class Login extends Component {

  constructor() {
    super()
    this.state = {
      username: '',
      password: '',
      alertMessage: null,
      player: null
    }
  }

  componentDidMount() {
    this.check();
  }

  check() {
    User.status()
      .then(user => {
        this.setState({player: user})
        if(this.props.onLogin) {
          this.props.onLogin(user);
        }
      });
  }

  login() {
    this.setState({alertMessage: null})
    User.loginAs(this.state.username, this.state.password)
    .then(result => {
      this.setState({player: result})
      if(this.props.onLogin) {
        this.props.onLogin(result);
      }
    })
    .catch(err => {
      this.setState({alertMessage: 'Incorrect Login'})
    })
  }

  signup() {
    User.signup(this.state.username, this.state.password)
    .then(result => {
      this.setState({alertMessage: "Signup complete successfully."})
      this.check();
    })
  }

  render() {
    if(this.state.player == null) {
      return (
        <div className="login-grid">
        <div className="login-form">
          <label>Login</label>
          <input type="text" value={this.state.username} onChange={e => this.setState({username: e.target.value})} />
          <label>Password</label>
          <input type="password" value={this.state.password} onChange={e => this.setState({password: e.target.value})} />
          <button onClick={e => this.signup()}>Signup</button>
          <button onClick={e => this.login()}>Login</button>
          <div hidden={!this.state.alertMessage} className="alert">{this.state.alertMessage}</div>
        </div>
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
