package oauth.authorization.jwe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oauth.authorization.security.SecretKeyProvider;

@Configuration
public class AppTestConfig {
	
	@Bean 
	public SecretKeyProvider keyProvider() {
		return new SecretKeyProvider();
	}

}
