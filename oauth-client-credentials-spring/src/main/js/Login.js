import React from 'react';
import ReactDOM from 'react-dom';

import Foo from './Foo';

class Login extends React.Component {
	
	constructor(props) {
		super(props);
		this.state = {loggedIn:false, message:''}
		this.handleLogin = this.handleLogin.bind(this)
		this.handleLogout = this.handleLogout.bind(this)
		this.clearMessage = this.clearMessage.bind(this)
		this.handleKeyPress = this.handleKeyPress.bind(this)
	}
	
	handleLogin(e) {		
		e.preventDefault();
		let username = ReactDOM.findDOMNode(this.refs["username"]).value.trim()
		let password = ReactDOM.findDOMNode(this.refs["password"]).value.trim()
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/", true);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr.send("username="+username+"&password="+password);
		
		xhr.onreadystatechange = (function(o) {
		    return function() {
		    	if (xhr.responseURL.indexOf('/?error') == -1) { 
		    		sessionStorage.loggedIn = true
		    		o.setState({loggedIn:true, message:''})
		    	} else {
		    		o.setState({message:'Login failed. Please check your username and password.'})
		    	}	
		    }
		})(this);		
	}
	
	handleLogout(e) {
		e.preventDefault();
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "/logout");
		xhr.send();
		xhr.onreadystatechange = (function(o) {
		    return function() {
		    	if (xhr.status != 200) { 	 
					alert('Logout failed.');		    		
		    	} 
				sessionStorage.removeItem('loggedIn');
				o.setState({loggedIn:false, message:''})					
		    }
		})(this);
	}
	
	clearMessage(e) {
		e.preventDefault();
		this.setState({message:''});
	}

	handleKeyPress(e) {
		if (e.key == 'Enter') {
			e.preventDefault();
			this.handleLogin(e);			
		}
	}
	
	render() {
		if (sessionStorage.loggedIn) {
			return (
				<div>
				 	<button className="btn btn-default pull-right" onClick={this.handleLogout}>Log out</button>
				 	<Foo />
				</div>
			)
		} else {
			return (
				<div className="login-container">									
				 	<input type="text" placeholder="username" onFocus={this.clearMessage} onKeyPress={this.handleKeyPress} ref="username"/>
		            <input type="password" placeholder="password" ref="password" onKeyPress={this.handleKeyPress} />
		            <button className="btn btn-primary" onClick={this.handleLogin}>Login</button>
		            <p></p>
		            <div className="alert alert-danger" style={(this.state.message) ? {} : {'display':'none'}} >
						{this.state.message}
					</div>	
		        </div>
			)
		}
	}
}

export default Login