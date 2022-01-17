package eu.palantir.portal.readiness;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import eu.palantir.portal.exceptions.MinioException;
import eu.palantir.portal.service.StorageService;

@Readiness
@ApplicationScoped
public class MinioReadinessCheck implements HealthCheck {

    private static final Logger logger = Logger.getLogger(MinioReadinessCheck.class.getName());

    @Inject
    StorageService sutil;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Minio Readiness check");
        try {
            isMinioReady();
            responseBuilder.up();
        } catch (MinioException e) {
            // cannot interact with the microservice
            responseBuilder.down();
        }
        return responseBuilder.build();
    }

    public void isMinioReady() {
        try {
            sutil.createBucket("test");
        } catch (Exception ex) {
            logger.severe("Minio is not properly configured");
            throw new MinioException("Minio is not properly configured");
        }

    }

}
