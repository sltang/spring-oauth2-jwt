import React from 'react';
import ReactDOM from 'react-dom';

import Foo from './Foo';

class Login extends React.Component {
	
	constructor(props) {
		super(props);
		this.state = {message:''}
		this.handleLogin = this.handleLogin.bind(this)
		this.handleLogout = this.handleLogout.bind(this)
		this.clearMessage = this.clearMessage.bind(this)
	}
	
	handleLogin(e) {		
		e.preventDefault();
		let username = ReactDOM.findDOMNode(this.refs["username"]).value.trim()
		let password = ReactDOM.findDOMNode(this.refs["password"]).value.trim()
		
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/login", true);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr.send("username="+username+"&password="+password);
		
		xhr.onreadystatechange = (function(o) {
		    return function() {
		    	if (xhr.status == 200) { 
		    		sessionStorage.loggedIn = true
	            	o.setState({message:''})
		    	} else {
		    		o.setState({message:'Failed to log in. Check your username and password.'})
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
		    	if (xhr.status == 200) { 	 
		    		sessionStorage.removeItem('loggedIn')
		    		o.setState({message:''})
		    	} else {
		    		sessionStorage.removeItem('loggedIn')
		    		o.setState({message:'Failed to log out.'})		    				    		
		    	}	
		    }
		})(this);
	}
	
	clearMessage(e) {
		e.preventDefault();
		this.setState({message:''});
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
				 	<input type="text" name="username" placeholder="username" onFocus={this.clearMessage} ref="username"/>
		            <input type="password" name="password" placeholder="password" ref="password"/>
		            <button className="btn btn-primary" onClick={this.handleLogin}>Login</button>
		            <div className="alert alert-warning" style={(this.state.message) ? {} : {'display':'none'}} >
						{this.state.message}
					</div>	
		        </div>
			)
		}
	}
}

export default Login