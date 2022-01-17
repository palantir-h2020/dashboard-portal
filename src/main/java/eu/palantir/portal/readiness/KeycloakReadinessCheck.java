package eu.palantir.portal.readiness;

import eu.palantir.portal.dto.AuthToken;
import eu.palantir.portal.exceptions.KeycloakException;
import eu.palantir.portal.model.Role;
import eu.palantir.portal.service.auth.AuthService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.keycloak.representations.idm.UserRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

@Readiness
@ApplicationScoped
public class KeycloakReadinessCheck implements HealthCheck {

    private static final Logger logger = Logger.getLogger(KeycloakReadinessCheck.class.getName());

    @Inject
    AuthService authService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Keycloak Readiness check");
        try {
            isKeycloakReady();
            responseBuilder.up();
        } catch (KeycloakException e) {
            // cannot interact with the microservice
            responseBuilder.down();
        }
        return responseBuilder.build();
    }

    public void isKeycloakReady() {
        AuthToken adminAuthToken = authService.getAdminAccessToken();
        if (adminAuthToken == null) {
            logger.severe("Keycloak Readiness failed - Keycloak URL or Admin credentials are not properly configured");
            throw new KeycloakException("Keycloak URL or Admin credentials of Keycloak are not properly configured");
        } else {
            if (authService.getUsersCount() == -1) {
                logger.severe("Keycloak Readiness failed - Realm not properly configured");
                throw new KeycloakException("Realm of Keycloak is not properly configured");
            }
            // add a temp user (we could substitute this business logic with a plain login
            // of trainer/trainer; yet it is safer)
            Random rand = new Random();
            UserRepresentation user = new UserRepresentation();
            String tmpUsername = "temp" + rand.nextInt(1000);
            user.setUsername(tmpUsername);
            user.setEmail(tmpUsername + "@ubitech.eu");
            user.setEnabled(true);
            try {
                authService.addUserKeycloak(user, Arrays.asList(Role.SME_MANAGER.toString()), user.getUsername());
                user = authService.getUserByUsernameKeycloak(tmpUsername);
                user.setRequiredActions(new ArrayList<>());
                user.setEmailVerified(true);
                authService.updateUser(user);
                authService.login(tmpUsername, tmpUsername);
            } catch (Exception ex) {
                logger.severe(ex.getMessage());
                logger.severe("Keycloak Readiness failed - Realm secret not properly configured");
                throw new KeycloakException("Realm secret of Keycloak is not properly configured");
            } finally {
                authService.deleteUserKeycloak(tmpUsername);
            }

        }

    }

}
