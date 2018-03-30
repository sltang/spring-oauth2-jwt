# Spring Security, OAuth2, JWT
This project contains a number of sample applications on Spring Security, OAuth2 and JWT:

- oauth-authorization-spring
- oauth-authorization-jwe-spring
- oauth-resource-spring
- oauth-resource-jwe-spring
- ouath-openid-connect
- oauth-client-credentials-spring
- oauth-code-angular
- oauth-code-react

The Spring applications can be started with:

`mvn spring-boot:run`

oauth-authorization-spring is a Spring authorization server that supports JWT. oauth-resource-spring is a Spring resource server that supports JWT. They should be run together.

oauth-authorization-jwe-spring is a Spring authorization server that supports JWE. oauth-resource-spring is a Spring resource server that supports JWE. They should be run together. Spring does not officially support JWE yet. These projects provide a simple addition that makes JWE work. Under the hood, we use connect2id's nimbus-jose-jwt for JWT encryption.

The authorization server runs https on port 8443 and the resource server runs http on 8082. The authorization server should be started before the resource server.

ouath-openid-connect is a simple Spring application that shows how we can do OAuth2 Login. It should also work with OpenID Connect without modifications. It can be accessed at `http://localhost:8080`.

oauth-client-credentials-spring is an application that uses standard Spring login for authentication. The data from the backend, however, is obtained through a client credentials grant type service-to-service API call. You can log in (with user:password) at:

`http://localhost:8088/`

oauth-code-angular and oauth-code-react are two simple SPAs that use the authorization code grant type. You can start them by:

`npm install`

`npm start`

and access them at:

`http://localhost:8083` (React)

`http://localhost:8086` (Angular)

More details can be found [here.](https://sltang.wordpress.com/2018/03/26/spring-security-oauth2-and-jwt/) 
