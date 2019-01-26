import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import './index.scss';

import Main from './Components/Main';

ReactDOM.render(<Router>
  <Route exact path="/" component={Home} />
  <Route path="/about" component={About} />
  <Route path="/topics" component={Topics} />
</Router>, document.getElementById('root'));
