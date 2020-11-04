package hmim.eteam.rest.backend.repository.core;

import hmim.eteam.rest.backend.entity.core.SiteUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiteUserRepository extends CrudRepository<SiteUser, Long> {
   List<SiteUser> findByLoginMD5AndPasswordMD5(String loginMD5, String passwordMD5);
}
