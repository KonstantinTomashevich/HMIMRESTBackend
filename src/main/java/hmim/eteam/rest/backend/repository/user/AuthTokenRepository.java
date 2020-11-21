package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.user.AuthToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface AuthTokenRepository extends CrudRepository<AuthToken, String> {
    default Optional<AuthToken> resolveToken(String token) {
        Optional<AuthToken> tokenFound = findById(token);
        if (tokenFound.isPresent()) {
            if (new Date().after(tokenFound.get().getExpireDate())) {
                delete(tokenFound.get());
                return Optional.empty();
            } else {
                return tokenFound;
            }
        } else {
            return Optional.empty();
        }
    }
}
