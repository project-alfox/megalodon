import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

import './index.scss';

import Main from './Components/Main';

ReactDOM.render(<Router>
  <Route exact path="/" component={Main} />
</Router>, document.getElementById('root'));
