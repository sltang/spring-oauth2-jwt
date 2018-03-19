import React, { Component } from 'react';
import './App.css';
import LogIn from './LogIn'

class App extends Component {
  render() {
    return (			
      <div className="App">
				<nav className="navbar navbar-default">
					<div className="container-fluid">
						<div className="navbar-header">
							<a className="navbar-brand" href="/">Spring Security OAuth - Authorization Code</a>
						</div>
					</div>
				</nav>
				<LogIn />
      </div>
    );
  }
}

export default App;
