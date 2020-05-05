import React from 'react';
import App from './components/app';
import {Route, Switch} from "react-router-dom";
import LoginPage from "./components/login-page/login";
import SignUpPage from "./components/signUp-page/signUp";

const Routes = () => {
    return (<Switch>
        <Route path = '/' exact component = {App}/>
        <Route path = '/login' component = {LoginPage}/>
        <Route path = '/signUp' component = {SignUpPage}/>
    </Switch>)
}

export default Routes