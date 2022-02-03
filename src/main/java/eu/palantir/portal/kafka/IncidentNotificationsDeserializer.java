package eu.palantir.portal.kafka;

import eu.palantir.portal.dto.message.IncidentNotification;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class IncidentNotificationsDeserializer extends ObjectMapperDeserializer<IncidentNotification> {

    public IncidentNotificationsDeserializer() {
        super(IncidentNotification.class);
    }

}
