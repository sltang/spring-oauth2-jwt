package oauth.resource.security;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SecretKeyProvider {
	
	@Value("${oauth.server.url}")
	private String oauthServerUrl;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public String getVerifierKey() throws IOException {
		URL url = new ClassPathResource("cacerts.jks").getURL();
		System.setProperty("javax.net.ssl.trustStore", url.getFile());
		//paraphrase chosen when the jks is created. You will have to choose your own paraphrase with your jks.
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
	    Map<String, String> result=restTemplate.getForObject(oauthServerUrl+"/oauth/token_key", Map.class);	
		return result.get("value");
	}

}
