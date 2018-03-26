package oauth.client_credentials;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ClientCredentialsSecurityConfig  extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http.antMatcher("/**").authorizeRequests()
		.antMatchers("/**").permitAll();
	}

}
