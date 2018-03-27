package oauth.resource.security;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SecretKeyProvider {
	
	@Value("${client.ssl.key}")
    private String clientKeyPath;
	
	@Value("${oauth.server.url}")
	private String oauthServerUrl;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public PrivateKey getPrivateKey() throws CertificateException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		File pem = new ClassPathResource(clientKeyPath).getFile();
		InputStream is = new FileInputStream(pem);
        DataInputStream dis = new DataInputStream(is);
        byte[] keyBytes = new byte[(int) pem.length()];
        dis.readFully(keyBytes);
        dis.close();
        is.close();
        
        String temp = new String(keyBytes);
        String privateKey = temp.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKey = privateKey.replace("-----END PRIVATE KEY-----", "");

        byte[] decoded = Base64.decode(privateKey);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);	
	}
	
	public String getVerifierKey() throws IOException {
		URL url = new ClassPathResource("cacerts.jks").getURL();
		System.setProperty("javax.net.ssl.trustStore", url.getFile());
		//paraphrase chosen when the jks is created. You will have to choose your own paraphrase with your jks.
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
	    Map<String, String> result = restTemplate.getForObject(oauthServerUrl+"/oauth/token_key", Map.class);	
		return result.get("value");
	}

}
