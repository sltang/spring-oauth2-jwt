'use strict';

import React from 'react';
import ReactDOM from 'react-dom';

import Login from './Login';

class App extends React.Component {
  render() {
    return (			
      <div className="App">
		<nav className="navbar navbar-default">
			<div className="container-fluid">
				<div className="navbar-header">
					<a className="navbar-brand" href="/">Spring Web</a>
				</div>
			</div>
		</nav>
		<Login />
      </div>
    );
  }
}

ReactDOM.render(
	<App />,
	document.getElementById('app')
)