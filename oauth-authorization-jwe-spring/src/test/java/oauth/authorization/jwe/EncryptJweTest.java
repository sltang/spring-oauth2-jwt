package oauth.authorization.jwe;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;

import oauth.authorization.security.SecretKeyProvider;
import oauth.authorization.token.store.JweAccessTokenConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes={AppTestConfig.class})
public class EncryptJweTest {
	
	private String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyX2FwcGxpY2F0aW9uIl0sInVzZXJfbmFtZSI6InVzZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTIxMTgxMTc2LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYzc5MWMyN2EtOGRlMC00OWM5LWFhOGQtNDU1OTgwMmM1YzFhIiwiY2xpZW50X2lkIjoidHJ1c3RlZC1hcHAifQ.CbeYCeQ5cm3etCmhQ_JHw8D6pknDAkcjbylPDBDm6z4";
	
	@Autowired
	private SecretKeyProvider keyProvider;
	
	@Test
	public void testEncrypJwt() throws CertificateException, IOException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		JweAccessTokenConverter converter = new JweAccessTokenConverter();
		converter.setPublicKey(keyProvider.getClientPublicKey())
		.setJweAlgo(JWEAlgorithm.RSA_OAEP_256)
		.setEncMethod(EncryptionMethod.A256GCM);
		//.setSigningKey(keyProvider.getKey());
		Signer signer = new RsaSigner(((RSAPrivateKey) keyProvider.getKey()));
        converter.setSigner(signer);
        converter.setVerifierKey(keyProvider.getPublicKey());
		String jweString =	converter.encryptJwt(jwt);
		assertNotNull("Payload not a signed JWT", jweString);
	}

}
