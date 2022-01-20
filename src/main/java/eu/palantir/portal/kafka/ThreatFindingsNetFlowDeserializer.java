package eu.palantir.portal.kafka;

import eu.palantir.portal.dto.message.ti.ThreatFindingNetFlow;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ThreatFindingsNetFlowDeserializer extends ObjectMapperDeserializer<ThreatFindingNetFlow> {

    public ThreatFindingsNetFlowDeserializer() {
        super(ThreatFindingNetFlow.class);
    }

}
