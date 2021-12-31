package eu.ubitech.palantir.portal;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.eclipse.microprofile.health.Readiness;
import org.hibernate.search.mapper.orm.session.SearchSession;

import eu.ubitech.palantir.portal.readiness.ElasticReadinessCheck;
import eu.ubitech.palantir.portal.readiness.KeycloakReadinessCheck;
import eu.ubitech.palantir.portal.readiness.MinioReadinessCheck;

@ApplicationScoped
public class Application {

  private static final Logger LOG = Logger.getLogger(Application.class.getName());

  public Application() {
  }

  @Inject
  @Readiness
  KeycloakReadinessCheck keycloakreadinesscheck;

  @Inject
  @Readiness
  ElasticReadinessCheck elasticreadinesscheck;

  @Inject
  @Readiness
  MinioReadinessCheck minioreadinesscheck;

  @Inject
  SearchSession searchSession;

  @Transactional
  void onStart(@Observes StartupEvent ev) {
    LOG.info("The application is starting...");
    LOG.info("Default timezone: " + TimeZone.getDefault().getDisplayName() + " with id " + ZoneId.systemDefault());
    // Readiness checks
    try {
      keycloakreadinesscheck.isKeycloakReady();
      LOG.info("Keycloak is Ready...");
      elasticreadinesscheck.isElasticReady();
      LOG.info("Elastic is Ready...");
      minioreadinesscheck.isMinioReady();
      LOG.info("Minio is Ready...");
    } catch (Exception ex) {
      LOG.severe("Readiness Exception ... Exiting Quarkus Backend");
      Quarkus.asyncExit();
    }

    // mass reindexing
    try {
      searchSession.massIndexer().startAndWait();
    } catch (InterruptedException ex) {
      LOG.severe("Mass re-Indexing error:" + ex.getMessage());
    }
  }

  @Transactional
  void onStop(@Observes ShutdownEvent ev) {
    LOG.info("The application is stopping...");
  }

}
