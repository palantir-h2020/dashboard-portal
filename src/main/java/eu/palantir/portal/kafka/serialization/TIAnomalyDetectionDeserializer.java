package eu.palantir.portal.kafka.serialization;

import eu.palantir.portal.dto.message.ti.TIAnomaly;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class TIAnomalyDetectionDeserializer extends ObjectMapperDeserializer<TIAnomaly> {

    public TIAnomalyDetectionDeserializer() {
        super(TIAnomaly.class);
    }

}
