package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.model.SelfInfo;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserController {
    private final AuthTokenRepository authTokenRepository;

    public UserController(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    public ResponseEntity<SelfInfo> me(String token) {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return authTokenRepository.resolveToken(token).
                map(value -> new ResponseEntity<>(value.getUser().toApiRepresentation(), HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
