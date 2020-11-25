package hmim.eteam.rest.backend.api;

import hmim.eteam.rest.backend.entity.user.UserRole;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public interface IRoleResolver {
    UserRole resolve(@NotNull String authTokenId, @Nullable Long courseId);
}
