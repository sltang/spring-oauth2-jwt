import React, { Component } from 'react';
import Foo from './Foo';

class LogIn extends Component {

	constructor(props) {
		super(props);
		this.handleLogin = this.handleLogin.bind(this);
		this.handleLogout = this.handleLogout.bind(this);
		this.state = {loggedIn:false, oauthServiceUrl:'https://localhost:8443/oauth/authorize'};
	}

	componentDidMount() {
		if (typeof(window.sessionStorage.accessToken) !== 'undefined') {
			this.setState({loggedIn:true});
		}
	}

	handleLogin(e) {
		e.preventDefault();
		window.location.href = this.state.oauthServiceUrl + '?response_type=code&client_id=normal-app&redirect_uri=http%3A%2F%2Flocalhost:8083%2F';
	}

	handleLogout(e) {
		e.preventDefault();
		window.sessionStorage.removeItem('accessToken');
    this.setState({loggedIn:false});
	}

	render() {
		if (!this.state.loggedIn) {	
			return (
					<button className="btn btn-primary" onClick={this.handleLogin}>Login</button>
			)			
		} else {
				return (
						<div className="content">
						  <span>Welcome !!</span>
						  <a className="btn btn-default pull-right" onClick={this.handleLogout}>Logout</a>
						  <br/>
						  	<Foo />
		  		</div>	
				)				
			}
	}

}

export default LogIn
	
