package eu.palantir.portal.kafka.serialization;

import eu.palantir.portal.dto.message.sm.SMResponse;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class SMResponsesDeserializer extends ObjectMapperDeserializer<SMResponse> {

    public SMResponsesDeserializer() {
        super(SMResponse.class);
    }

}
