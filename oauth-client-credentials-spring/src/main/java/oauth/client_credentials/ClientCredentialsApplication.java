package oauth.client_credentials;

import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientCredentialsApplication {

	public static void main(String[] args) {
		//development setup: trust authorization server's SSL certificate
		URL url = ClientCredentialsApplication.class.getClassLoader().getResource("cacerts.jks");
		System.setProperty("javax.net.ssl.trustStore", url.getFile());
		//paraphrase chosen when the jks is created. You will have to choose your own paraphrase with your jks.
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		SpringApplication.run(ClientCredentialsApplication.class, args);
	}

}
