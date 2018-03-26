import React, { Component } from 'react';

var rest = require('rest');
var mime = require('rest/interceptor/mime');

class Foo extends Component {
	
	constructor(props) {
		super(props);
		let client = rest.wrap(mime);
		this.state = {foosUrl:'http://localhost:8082/api/foos/', client:client, fooId:1, fooName:'Foo'};
		this.handleGetFoo = this.handleGetFoo.bind(this);
		this.getAccessToken = this.getAccessToken.bind(this);
	}

	getAccessToken() {
		if (typeof(sessionStorage.accessToken) !== 'undefined') {
			return JSON.parse(sessionStorage.accessToken)['access_token'];
		}
		return null;
	}
	
	handleGetFoo(e) {
		e.preventDefault();
		this.state.client({
				method: 'GET',
				path: this.state.foosUrl + this.state.fooId,
				headers: {
				    'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 
						'Authorization': 'Bearer '+this.getAccessToken()
				}
		}).then((response) => {
				return response.entity;
		}).then(entity => {
				this.setState({fooId:entity.id, fooName:entity.name});
		});
	}

	render() {
		return (
				<div className="container">
					<h1 className="col-sm-12">Foo Details</h1>
					<div className="col-sm-12">
						  <label className="col-sm-3">ID</label> <span>{this.state.fooId}</span>
					</div>
					<div className="col-sm-12">
						  <label className="col-sm-3">Name</label> <span>{this.state.fooName}</span>
					</div>
					<div className="col-sm-12">
						  <button className="btn btn-primary" onClick={this.handleGetFoo}>New Foo</button>        
					</div>
			</div>
		)
	}
}

export default Foo;
