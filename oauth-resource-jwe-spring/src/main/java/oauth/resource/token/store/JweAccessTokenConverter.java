package oauth.resource.token.store;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;

public class JweAccessTokenConverter extends JwtAccessTokenConverter {
	
	private PrivateKey decryptKey;
	private PublicKey publicKey;
	private JWEAlgorithm jweAlgo;
	private EncryptionMethod encMethod;
	
	@Override
	protected Map<String, Object> decode(String token) {
		token = decryptJwe(token);
		return super.decode(token);
	}
	
	@Override
	protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		String token = super.encode(accessToken, authentication);
		return encryptJwt(token);
	}
	
	/**
	 * set private key to decrypt JWE by audience
	 * @param decryptKey
	 * @return 
	 */
	public JweAccessTokenConverter setDecryptKey(PrivateKey decryptKey) {
		this.decryptKey = decryptKey;
		return this;
	}

	/**
	 * set public key to encrypt JWT by issuer
	 * @param publicKey
	 * @return
	 */
	public JweAccessTokenConverter setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
		return this;
	}
	
	/**
	 * set algorithm for CEK
	 * @param jweAlgo
	 * @return
	 */
	public JweAccessTokenConverter setJweAlgo(JWEAlgorithm jweAlgo) {
		this.jweAlgo = jweAlgo;
		return this;
	}

	/**
	 * set JWE algorithm to encrypt CEK
	 * @param encMethod
	 * @return
	 */
	public JweAccessTokenConverter setEncMethod(EncryptionMethod encMethod) {
		this.encMethod = encMethod;
		return this;
	}

	/**
	 * use nimbus-jose-jwt to encrypt signed token using public key
	 * @param jweString
	 * @return
	 */
	public String encryptJwt(String jwtString) {
		try {
			String[] parts = jwtString.split("\\.");
			SignedJWT signedJWT = new SignedJWT(new Base64URL(parts[0]), new Base64URL(parts[1]), new Base64URL(parts[2]));
	
			JWEObject jweObject = new JWEObject(
					new JWEHeader.Builder(jweAlgo, encMethod)
				        .contentType("JWT") // required to signal nested JWT
				        .build(),
				    new Payload(signedJWT));
		
			RSAEncrypter encrypter = new RSAEncrypter((RSAPublicKey) publicKey);
			// Perform encryption
			jweObject.encrypt(encrypter);
	
			// Serialise to JWE compact form
			String jweString = jweObject.serialize();
			return jweString;
		} catch (ParseException | JOSEException e) {
			throw new InvalidTokenException("Cannot encrypt access token", e);
		}
	}

	/**
	 * use nimbus-jose-jwt to decrypt encrypted token using private key
	 * @param jwtString
	 * @return
	 */
	public String decryptJwe(String jweString) {	
		try {
			JWEObject jweObj = JWEObject.parse(jweString);
			RSADecrypter decrypter = new RSADecrypter(decryptKey);
			jweObj.decrypt(decrypter);			
			SignedJWT decyrptedJwt = jweObj.getPayload().toSignedJWT();
			return decyrptedJwt.getParsedString();
		} catch (ParseException | JOSEException e) {
			throw new InvalidTokenException("Cannot decrypt access token", e);
		}
	}	

}
