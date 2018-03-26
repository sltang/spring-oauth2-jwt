package oauth.resource;

import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;

public class CustomClaimVerifier implements JwtClaimsSetVerifier { 
	
	private static final String ROLE_TRUSTED_APP = "ROLE_TRUSTED_APP";
	
    @Override
    public void verify(Map<String, Object> claims) throws InvalidTokenException {
    	List<String> roles = (List<String>) claims.get("authorities");
    	if (roles.contains(ROLE_TRUSTED_APP)) return;
        String username = (String) claims.get("user_name");
        if ((username == null) || (username.length() == 0)) {
            throw new InvalidTokenException("user_name claim is empty");
        }
    }
    
}
