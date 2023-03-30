package com.lms.authservice.jwt.tokens.refreshtoken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	
	// FIXME: Fina a better way to do this
	default void deleteAllByEmail(String email) {
		this.findAll()
		    .forEach(refreshToken -> {
			    if (refreshToken.getEmail()
			                    .equals(email)) {
				    this.deleteById(refreshToken.getToken());
			    }
		    });
	}
}
