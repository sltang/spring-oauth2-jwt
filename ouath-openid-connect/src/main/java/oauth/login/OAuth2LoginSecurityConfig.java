package oauth.login;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.antMatcher("/**").authorizeRequests()
		.antMatchers("/", "/webjars/**").permitAll()
	      .anyRequest()
	      .authenticated()
	      .and()
	      .oauth2Login()
	      .loginPage("/")
	      .defaultSuccessUrl("/index")
	      .and().logout().permitAll()
	      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	      
	}

}
