package eu.palantir.portal.kafka.serialization;

import eu.palantir.portal.dto.message.ti.ThreadFindingSysLog;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ThreatFindingsSysLogDeserializer extends ObjectMapperDeserializer<ThreadFindingSysLog> {

    public ThreatFindingsSysLogDeserializer() {
        super(ThreadFindingSysLog.class);
    }

}
