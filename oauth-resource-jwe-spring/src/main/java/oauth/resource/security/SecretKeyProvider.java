package oauth.resource.security;

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
    private String certPath;
	
	@Value("${client.ssl.key}")
    private String clientKeyPath;
	
	public String getKey() throws CertificateException, IOException {
		InputStream is = new ClassPathResource(certPath).getInputStream();
    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	X509Certificate certificate = (X509Certificate) cf.generateCertificate(is);
    	PublicKey pk = certificate.getPublicKey();
    	is.close();
    	return new String(pk.getEncoded(), "UTF-8"); 
    }
	
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

}
