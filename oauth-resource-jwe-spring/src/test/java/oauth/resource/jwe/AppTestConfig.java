package oauth.resource.jwe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oauth.resource.security.SecretKeyProvider;

@Configuration
public class AppTestConfig {
	
	@Bean 
	public SecretKeyProvider keyProvider() {
		return new SecretKeyProvider();
	}

}
