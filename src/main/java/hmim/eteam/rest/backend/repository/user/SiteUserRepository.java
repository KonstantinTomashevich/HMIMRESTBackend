package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.user.SiteUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SiteUserRepository extends CrudRepository<SiteUser, Long> {
   Optional<SiteUser> findFirstByLoginMD5AndPasswordMD5(String loginMD5, String passwordMD5);
   boolean existsByLoginMD5(String loginMD5);
}
