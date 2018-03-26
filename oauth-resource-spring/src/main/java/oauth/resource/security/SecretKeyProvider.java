package oauth.resource.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class SecretKeyProvider {
	
	@Value("${server.ssl.cert}")
    private String certPath;
	
	public String getKey() throws CertificateException, IOException {
		InputStream is = new ClassPathResource(certPath).getInputStream();
    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	X509Certificate certificate = (X509Certificate) cf.generateCertificate(is);
    	PublicKey pk = certificate.getPublicKey();
    	return new String(pk.getEncoded(), "UTF-8"); 
    }

}
