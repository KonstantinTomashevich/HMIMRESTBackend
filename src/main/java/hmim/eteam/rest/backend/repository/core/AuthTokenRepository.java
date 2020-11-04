package hmim.eteam.rest.backend.repository.core;

import hmim.eteam.rest.backend.entity.core.AuthToken;
import org.springframework.data.repository.CrudRepository;

public interface AuthTokenRepository extends CrudRepository<AuthToken, Long> {
}
