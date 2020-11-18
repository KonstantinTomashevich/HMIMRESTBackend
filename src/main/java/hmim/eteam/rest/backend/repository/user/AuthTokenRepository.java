package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.user.AuthToken;
import org.springframework.data.repository.CrudRepository;

public interface AuthTokenRepository extends CrudRepository<AuthToken, String> {
}
