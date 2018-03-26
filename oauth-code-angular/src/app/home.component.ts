import {Component} from '@angular/core';
import {AppService} from './app.service'
 
@Component({
    selector: 'home-header',
    providers: [AppService],
  template: `<div class="container">
    <button *ngIf="!isLoggedIn" class="btn btn-primary" (click)="login()" type="submit">Login</button>
    <div *ngIf="isLoggedIn" class="content">
        <span>Welcome {{username}}!!</span>
        <a class="btn btn-default pull-right"(click)="logout()" href="#">Logout</a>
        <br/>
        <foo-details></foo-details>
    </div>
</div>`
})
 
export class HomeComponent {
	
  public isLoggedIn = false;
	public oauthServiceUrl = 'https://localhost:8443/oauth/authorize';
	public username = '';

  constructor(private _service:AppService){}
  
  ngOnInit(){
      this.isLoggedIn = this._service.isLoggedIn();
			if (this.isLoggedIn) {
				this.username = this._service.getUsername();
			}
  }

  login() {
    window.location.href = this.oauthServiceUrl + '?response_type=code&client_id=code-angular&redirect_uri=http%3A%2F%2Flocalhost:8086%2F';
  }

  logout() {
      this._service.logout();
  }

}
