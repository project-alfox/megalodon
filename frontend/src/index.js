import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import './index.scss';

import Auth from './API/login';
import Main from './Components/Main';
import Login from './Components/Login';

class App extends React.Component {
  constructor() {
    super()
    this.state = {
      player: null
    }
  }

  render() {
    return <Router>
      <div>
      <Route  path="/" render={params =>
        (Auth.isLoggedIn || this.state.player)
        ? <Main player={this.state.player} />
        : <Login onLogin={player => {
          this.setState({ player })
        } } />
      }></Route>
      </div>
    </Router>
  }
}

ReactDOM.render(<App />, document.getElementById('root'));

/*
  const map = await this.refreshMap(player);
  this.setState({ player, map });
}


*/
