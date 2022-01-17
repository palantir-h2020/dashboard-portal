package eu.palantir.portal.service.auth;

import eu.palantir.portal.util.JwtClaim;
import io.quarkus.security.UnauthorizedException;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@RequestScoped
public class JwtService {

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(JwtService.class.getName());

    public Long getUserId() {
        if (!jwt.containsClaim(JwtClaim.USER_ID.getDescription())) {
            logger.warning("User does not have attribute " + JwtClaim.USER_ID.getDescription() + " Check keycloak");
            throw new UnauthorizedException();
        }
        return Long.parseLong(jwt.getClaim(JwtClaim.USER_ID.getDescription()).toString());
    }

    public void checkPermissions() {
    }

}
