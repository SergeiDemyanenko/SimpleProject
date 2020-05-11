import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from 'react-router-dom'
import Routes from "./Routes";

const Application = () => {
    return (<BrowserRouter>
        <Routes />
    </BrowserRouter>)
}

ReactDOM.render(<Application />,
  document.getElementById('root'));