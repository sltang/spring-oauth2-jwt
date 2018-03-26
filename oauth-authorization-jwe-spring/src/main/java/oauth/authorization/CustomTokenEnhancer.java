package oauth.authorization;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenEnhancer implements TokenEnhancer {
	
	private static final String ISS_CLAIM = "iss";
	private static final String SUBJECT_CLAIM = "sub";
	
	@Value("${oauth.server.url}")
	private String oauthServerUrl;
	
	@Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put(SUBJECT_CLAIM, authentication.getName());
        additionalInfo.put(ISS_CLAIM, oauthServerUrl);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
	}

}
