package oauth.authorization.security;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class SecretKeyProvider {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Value("${client.ssl.cert}")
    private String clientCertPath;	
	
	@Value("${server.ssl.key-store-type}")
	private String keyStoreType;
	
	@Value("${server.ssl.key-store-password}")
    private String keyStorePassword;
	
	@Value("${server.ssl.key-store}")
    private String keyStore;
	
	@Value("${server.ssl.cert}")
    private String pubKey;
	
	
	public PrivateKey getKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException  { 	
		KeyStore keystore = KeyStore.getInstance(keyStoreType);
		keystore.load(resourceLoader.getResource(keyStore).getInputStream(), keyStorePassword.toCharArray());
		PrivateKey key = (PrivateKey) keystore.getKey("1", keyStorePassword.toCharArray());
		return key;
    }
	
	public PublicKey getClientPublicKey() throws CertificateException, IOException {
		InputStream is = new ClassPathResource(clientCertPath).getInputStream();
    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	X509Certificate certificate = (X509Certificate) cf.generateCertificate(is);
    	is.close();
    	return certificate.getPublicKey();		
	}
	
	public String getPublicKey() throws CertificateException, IOException {
		try (FileReader reader = new FileReader(new ClassPathResource(pubKey).getFile());
			BufferedReader in = new BufferedReader(reader);) {		
			String key = "", line = "";
			while ((line = in.readLine())!=null) {
				key += line + "\n";
			}
			return key;
		}
    }

}
