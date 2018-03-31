package oauth.client_credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import oauth.client_credentials.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class ClientCredentialsSecurityConfig  extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(this.userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.logout()
	        	.logoutUrl("/logout")
	        	.logoutSuccessUrl("/")
	        	.invalidateHttpSession(true)
        	.and()
            	.authorizeRequests()
            	.antMatchers("/", "/*.css", "/bundle.js").permitAll()
                .anyRequest().authenticated()
            .and()
            	.csrf().disable()   
            .formLogin()
                .loginPage("/");                ;
    }

}
