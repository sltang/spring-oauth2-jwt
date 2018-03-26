package oauth.authorization.security;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class SecretKeyProvider {
	
	@Value("${server.ssl.cert}")
    private String serverCertPath;
	
	@Value("${client.ssl.cert}")
    private String clientCertPath;
	
	@Value("${client.ssl.key}")
    private String clientKeyPath;
	
	
	public String getKey() throws CertificateException, IOException {
		InputStream is = new ClassPathResource(serverCertPath).getInputStream();
    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	X509Certificate certificate = (X509Certificate) cf.generateCertificate(is);
    	PublicKey pk = certificate.getPublicKey();
    	return new String(pk.getEncoded(), "UTF-8");  	
    }
	
	public PublicKey getClientPublicKey() throws CertificateException, IOException {
		InputStream is = new ClassPathResource(clientCertPath).getInputStream();
    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	X509Certificate certificate = (X509Certificate) cf.generateCertificate(is);
    	is.close();
    	return certificate.getPublicKey();		
	}
	
	public PrivateKey getPrivateKey() throws CertificateException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		File pem = new ClassPathResource(clientKeyPath).getFile();
		InputStream is = new FileInputStream(pem);
        DataInputStream dis = new DataInputStream(is);
        byte[] keyBytes = new byte[(int) pem.length()];
        dis.readFully(keyBytes);
        dis.close();
        
        String temp = new String(keyBytes);
        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");

        byte[] decoded=Base64.decode(privKeyPEM);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);	
	}

}
