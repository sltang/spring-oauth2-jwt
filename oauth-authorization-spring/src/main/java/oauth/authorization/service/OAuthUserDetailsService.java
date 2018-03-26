package oauth.authorization.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import oauth.authorization.model.OAuthUser;
import oauth.authorization.repository.OAuthUserRepository;

@Service
public class OAuthUserDetailsService implements UserDetailsService {

	@Autowired
	private OAuthUserRepository oauthUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<OAuthUser> oauthUser = oauthUserRepository.findByUsername(username);
		if (oauthUser.isPresent()) {
            return oauthUser.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
	}

}
