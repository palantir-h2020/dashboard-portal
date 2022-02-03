package eu.palantir.portal.kafka;

import eu.palantir.portal.dto.message.ActionNotification;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ActionsNotificationsDeserializer extends ObjectMapperDeserializer<ActionNotification> {

    public ActionsNotificationsDeserializer() {
        super(ActionNotification.class);
    }

}
