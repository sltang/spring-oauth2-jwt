package oauth.authorization;

import java.util.LinkedHashMap;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class RemoveClaimsTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        DefaultOAuth2AccessToken t = new DefaultOAuth2AccessToken(accessToken);
        t.setAdditionalInformation(new LinkedHashMap<>());
        t.setExpiration(null);
        t.setScope(null);
        return t;

	}

}
