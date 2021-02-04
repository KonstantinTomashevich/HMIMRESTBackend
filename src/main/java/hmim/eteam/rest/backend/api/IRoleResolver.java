package hmim.eteam.rest.backend.api;

import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.user.UserRole;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface IRoleResolver {
    UserRole resolve(@NotNull String authTokenId, @Nullable Long courseId);
    UserRole resolve(@NotNull Optional<AuthToken> authToken, @Nullable Long courseId);
    UserRole resolve(@NotNull SiteUser siteUser, @Nullable Long courseId);
}
