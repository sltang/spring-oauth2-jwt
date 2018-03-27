package oauth.resource.jwe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nimbusds.jose.*;

import oauth.resource.security.SecretKeyProvider;
import oauth.resource.token.store.JweAccessTokenConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes={AppTestConfig.class})
public class DecryptJweTest {
	
	private String jweString = "eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.ewcLrZkSSCe-uP6Us3JrU4pnzmCm2QDh9MlFh-tNeYkqX6TyheiRtKXhF2D_FbK7AaX0HIdUmfOkgVT-20Tvx9XIN4Je5aviu189PfjyPZKl9nVlRNeYvNVY9kPsunM__FQ_IsUxoLxBxjr92uRf0D1-AJ-VhgMarD1_WMRQZnPzOTRpLyXS8ZbzN0K5OdJ_oXPvzAhX3AIEkqB6b9Ywu0DutNLmls7t9bILHFm_h19L2Y5NjChWFPm-Ptfnkr6ZDu9PnVsN__FrVAD9fk71R5z72yOU4d0VV6sQNA3BSqLhqS8NYItr-zo6tKhtFnJUO5Y22w1U8ewZegeu7aR2-g.JYW_OM4X2_Fx28tU.3SSjmpOPhdAcfDmVvJ_WYBLmgCM-3ccLr5m9bxgzYYTfYuXbvkglBGi5750y3QpWDaOBXEOkrI2jkI5CL7vrKMNV1k2y2h8tZ64iuA95RFaszKkxpIhxemtyjSTD6lhaeCM9YPILiGzij9TUmdL3lorwhBfTi6yg9Mu1glRVMbATV_a7QviN9W4juHq8EI7mwPtHaavbuVQAAFCIhnAUYYGG9Y15M24xQD4ekvwDMRdiNM6h4S1FESCxUZQe7jXgZKq3pzAkp8-qerKbL0jQ-sN2YmMuxHHaYeexFBmdi8j6XF6bnr0sQGYf22eZJQ710yDPja_H581Gafa9OuX5Yzj1QbkKiI9NJXMD5LaIf32fdA_hhvX-hCrQxQJYHWgEmWmUyIrOsG3npEudRTcGx5GRKFUT5qBfidKxVCk9o9PzOoz3XaX4px_1_Zpv7bE.X9Kno_xR10r-DiqqHFJS3Q";
	
	@Autowired
	private SecretKeyProvider keyProvider;
	
	@Value("${server.ssl.cert}")
	private String pubKey;
	
	@Test
	public void testDecryptJwe() throws ParseException, CertificateException, IOException, JOSEException, InvalidKeySpecException, NoSuchAlgorithmException {				
		JweAccessTokenConverter converter = new JweAccessTokenConverter();
			converter.setDecryptKey(keyProvider.getPrivateKey())
			.setJweAlgo(JWEAlgorithm.RSA_OAEP_256)
			.setEncMethod(EncryptionMethod.A256GCM)
			.setVerifierKey(getVerifierKey());
		
	    JWSObject jws = JWSObject.parse(converter.decryptJwe(jweString));
	    assertNotNull("Invalid JWS", jws);
		assertEquals("user", jws.getPayload().toJSONObject().get("user_name"));
	}
	
	public String getVerifierKey() throws CertificateException, IOException {
		FileReader reader = new FileReader(new ClassPathResource(pubKey).getFile());
		BufferedReader in = new BufferedReader(reader);		
		String key = "", line = "";
		while ((line = in.readLine())!=null) {
			key += line + "\n";
		}
		reader.close();
		in.close();
		return key;
    }

}
