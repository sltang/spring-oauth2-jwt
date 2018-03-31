package oauth.authorization.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import oauth.authorization.model.OAuthUser;

public interface OAuthUserRepository extends Repository<OAuthUser, Long> {
	Optional<OAuthUser> findByUsername(String username);
	OAuthUser save(OAuthUser user);
}
