package eu.palantir.portal.kafka.serialization;

import eu.palantir.portal.dto.message.ir.IRPortalNotification;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class IRNotificationDeserializer extends ObjectMapperDeserializer<IRPortalNotification> {

    public IRNotificationDeserializer() {
        super(IRPortalNotification.class);
    }

}
