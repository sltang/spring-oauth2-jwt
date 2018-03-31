import React from 'react';
import ReactDOM from 'react-dom';

var rest = require('rest');
var mime = require('rest/interceptor/mime');

class Foo extends React.Component {
	
	constructor(props) {
		super(props);
		let client = rest.wrap(mime);
		this.state = {foosUrl:'/api/foo/', client:client, fooId:1, fooName:'Foo'};
		this.handleGetFoo = this.handleGetFoo.bind(this);
	}
	
	handleGetFoo(e) {
		e.preventDefault();
		this.state.client({
			method: 'GET',
			path: this.state.foosUrl + this.state.fooId
		}).then((response) => {
				if (response.status.code == 200) {
					this.setState({fooId:response.entity.id, fooName:response.entity.name});
				} else {
					this.setState({fooName:'Error'});
				}
			},
			(response) => {
				console.error('response error: ', response);
			}
		);
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

export default Foo
