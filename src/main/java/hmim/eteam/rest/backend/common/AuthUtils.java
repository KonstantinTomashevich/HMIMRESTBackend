package hmim.eteam.rest.backend.common;

import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

public class AuthUtils {
    private AuthUtils() {
    }

    public static String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    }

    public static AuthToken generateToken(SiteUser siteUser, AuthTokenRepository repository) {
        // TODO: Cleanup expired tokens?
        Random random = new Random();
        String id;
        int triesLeft = Integer.MAX_VALUE;

        do {
            id = Integer.toHexString(random.nextInt());
            --triesLeft;
        } while (repository.existsById(id) || triesLeft == 0);

        return repository.save(new AuthToken(id, siteUser, new Date()));
    }

    public static Optional<AuthToken> resolveToken(String token, AuthTokenRepository repository) {
        Optional<AuthToken> tokenFound = repository.findById(token);
        if (tokenFound.isPresent()) {
            if (new Date().after(tokenFound.get().getExpireDate())) {
                repository.delete(tokenFound.get());
                return Optional.empty();
            } else {
                return tokenFound;
            }
        } else {
            return Optional.empty();
        }
    }

}
