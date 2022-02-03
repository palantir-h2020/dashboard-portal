package eu.palantir.portal.kafka;

import eu.palantir.portal.dto.message.rs.RSPortalNotification;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class RSNotificationDeserializer extends ObjectMapperDeserializer<RSPortalNotification> {

    public RSNotificationDeserializer() {
        super(RSPortalNotification.class);
    }

}
