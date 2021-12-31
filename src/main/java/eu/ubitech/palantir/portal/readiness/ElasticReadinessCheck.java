package eu.ubitech.palantir.portal.readiness;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.hibernate.search.mapper.orm.Search;

import eu.ubitech.palantir.portal.exceptions.ElasticException;
import eu.ubitech.palantir.portal.model.Person;

@Readiness
@ApplicationScoped
public class ElasticReadinessCheck implements HealthCheck {

  private static final Logger logger = Logger.getLogger(ElasticReadinessCheck.class.getName());

  @Inject
  EntityManager em;

  @Override
  public HealthCheckResponse call() {
    HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Elastic Readiness check");
    try {
      isElasticReady();
      responseBuilder.up();
    } catch (ElasticException e) {
      // cannot interact with the microservice
      responseBuilder.down();
    }
    return responseBuilder.build();
  }

  public void isElasticReady() {
    try {
      Search.session(em).search(Person.class).where(f -> f.match().fields("firstName").matching("test"))
          .fetchTotalHitCount();
    } catch (Exception ex) {
      logger.severe("Elastic Server is not responsive");
      throw new ElasticException("Elastic Server is not responsive");
    }

  }

}
