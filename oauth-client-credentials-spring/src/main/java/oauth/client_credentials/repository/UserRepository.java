package oauth.client_credentials.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import oauth.client_credentials.model.User;

public interface UserRepository extends Repository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
