package eu.ubitech.palantir.portal.readiness;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import eu.ubitech.palantir.portal.dto.Token;
import eu.ubitech.palantir.portal.exceptions.KeycloakException;
import eu.ubitech.palantir.portal.service.auth.AuthService;
import eu.ubitech.palantir.portal.service.auth.KeycloakUser;

@Readiness
@ApplicationScoped
public class KeycloakReadinessCheck implements HealthCheck {

  private static final Logger logger = Logger.getLogger(KeycloakReadinessCheck.class.getName());

  @Inject
  AuthService authservice;

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
    Token admintoken = authservice.fetchAccessToken();
    if (admintoken == null) {
      logger.severe("Keycloak Readiness failed - Keycloak URL or Admin credentials are not properly configured");
      throw new KeycloakException("Keycloak URL or Admin credentials of Keycloak are not properly configured");
    } else {
      if (authservice.fetchUsersCount(admintoken) == -1) {
        logger.severe("Keycloak Readiness failed - Realm not properly configured");
        logger.severe("Keycloak Readiness failed due to negative user count");
        throw new KeycloakException("Realm of Keycloak is not properly configured");
      }
      // add a temp user (we could substitute this business logic with a plain login
      // of trainer/trainer; yet it is safer)
      Random rand = new Random();
      KeycloakUser kuser = new KeycloakUser();
      String tmpusername = "temp" + rand.nextInt(1000);
      byte[] array = new byte[10]; // length is bounded by 10
      rand.nextBytes(array);
      String tmpPassword = new String(array, Charset.forName("UTF-8"));

      kuser.username = tmpusername;
      kuser.firstName = tmpusername;
      kuser.lastName = tmpusername;
      kuser.email = tmpusername + "@ubitech.eu";
      kuser.enabled = "true";
      kuser.realmRoles = new String[] { "trainer" };
      try {
        authservice.addUser(kuser, tmpPassword);
        Token userToken = authservice.login(tmpusername, tmpPassword);
      } catch (Exception ex) {
        logger.severe(ex.getMessage());
        logger.severe("Keycloak Readiness failed - Realm secret not properly configured");
        throw new KeycloakException("Realm secret of Keycloak is not properly configured");
      } finally {
        String userId = authservice.fetchUserIdByUsername(admintoken, tmpusername).id;
        authservice.deleteUser(admintoken, userId);
      }

    }

  }

}
