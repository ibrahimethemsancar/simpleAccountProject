import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Customer from './Customer';
import Account from "./Account";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/'  component={Customer}/>
                    <Route path='/account/:id'  component={Account}/>
                </Switch>
            </Router>
        )
    }
}

export default App;