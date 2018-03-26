import {Injectable} from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { HttpModule } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
 
export class Foo {
  constructor(
    public id: number,
    public name: string) { }
} 

@Injectable()
export class AppService {
 
  constructor(private _http: Http) {}

  getAccessToken() {
		if (typeof(sessionStorage.accessToken) !== 'undefined') {
			return JSON.parse(sessionStorage.accessToken)['access_token'];
		}
		return null;
  }

	getUsername() {
		if (typeof(sessionStorage.accessToken) !== 'undefined') {
			return JSON.parse(sessionStorage.accessToken)['sub'];//sub claim
		}
		return null;
	}

  getResource(resourceUrl) : Observable<Foo>{
    var headers = new Headers({'Content-type': 'application/x-www-form-urlencoded; charset=utf-8', 'Authorization': 'Bearer '+this.getAccessToken()});
    var options = new RequestOptions({ headers: headers });
    return this._http.get(resourceUrl, options)
                   .map((res:Response) => res.json())
                   .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }

  isLoggedIn(){ 
    if (this.getAccessToken() === null){
       return false;
    }
    return true;
  } 

  logout() {
	  sessionStorage.removeItem('accessToken');
    location.reload();
  }

}
