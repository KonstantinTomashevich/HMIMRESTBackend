package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.user.AuthToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

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
