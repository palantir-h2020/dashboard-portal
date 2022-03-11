package eu.palantir.portal.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import eu.palantir.portal.dto.message.IncidentNotification;
import eu.palantir.portal.dto.message.ir.IRPortalNotification;
import eu.palantir.portal.dto.message.rs.RSPortalNotification;
import eu.palantir.portal.dto.message.ti.NetFlowData;
import eu.palantir.portal.dto.message.ti.TIAnomaly;
import eu.palantir.portal.dto.message.ti.ThreadFindingSysLog;
import eu.palantir.portal.dto.message.ti.ThreatFinding;
import eu.palantir.portal.dto.message.ti.ThreatFindingNetFlow;

import org.mapstruct.InjectionStrategy;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IncidentMapper {

    // Specific incident to generic incident mappings

    @Mappings({
            @Mapping(target = "incidentType", constant = "threat:fsm"),
            @Mapping(target = "incidentDescription", expression = "java( irCompleteIncidentDescription(irPortalNotification) )"),
    })
    IncidentNotification toIncidentNotification(IRPortalNotification irPortalNotification);

    @Mappings({
            @Mapping(target = "incidentType", constant = "failed_attestation:fsm"),
            @Mapping(target = "detectedIncident", source = "failedAttestation"),
            @Mapping(target = "incidentLocation", source = "serviceIP"),
            @Mapping(target = "incidentDescription", expression = "java( rsCompleteIncidentDescription(rsPortalNotification) )"),
    })
    IncidentNotification toIncidentNotification(RSPortalNotification rsPortalNotification);

    @Mappings({
            @Mapping(target = "incidentType", constant = "threat:netflow"),
            @Mapping(target = "detectedIncident", expression = "java( netflowCompleteDetectedThreat(threatFindingNetFlow) )"),
            @Mapping(target = "incidentLocation", expression = "java( netflowThreatCompleteLocation(threatFindingNetFlow) )"),
            @Mapping(target = "incidentDescription", expression = "java( netflowThreatCompleteDescription(threatFindingNetFlow) )"),
    })
    IncidentNotification toIncidentNotification(ThreatFindingNetFlow threatFindingNetFlow);

    @Mappings({
            @Mapping(target = "incidentType", constant = "threat:syslog"),
            @Mapping(target = "detectedIncident", source = "threatLabel"),
            @Mapping(target = "incidentLocation", source = "sourceIP"),
            @Mapping(target = "incidentDescription", source = "anomalyDetectionSyslog"),
    })
    IncidentNotification toIncidentNotification(ThreadFindingSysLog threadFindingSysLog);

    @Mappings({
            @Mapping(target = "incidentType", constant = "anomaly"),
            @Mapping(target = "detectedIncident", source = "anomalyDetectionMethod"),
            @Mapping(target = "incidentLocation", expression = "java( completeAnomalyLocation(tiAnomaly) )"),
            @Mapping(target = "incidentDescription", expression = "java( completeAnomalyDescription(tiAnomaly) )"),
    })
    IncidentNotification toIncidentNotification(TIAnomaly tiAnomaly);

    // Conversion Helper Methods (Custom logic)

    default String irCompleteIncidentDescription(IRPortalNotification irPortalNotification) {
        return irPortalNotification.getMessage() + " : " + irPortalNotification.getIncidentDescription();
    }

    default String rsCompleteIncidentDescription(RSPortalNotification rsPortalNotification) {
        return "Incident at node with id "
                + rsPortalNotification.getNodeId()
                + " of type "
                + rsPortalNotification.getNodeType()
                + ": "
                + rsPortalNotification.getFailedDescription();
    }

    default String netflowCompleteDetectedThreat(ThreatFindingNetFlow threatFindingNetFlow) {
        return threatFindingNetFlow.getThreatCategory() + ":"
                + threatFindingNetFlow.getThreatLabel();
    }

    default String netflowThreatCompleteLocation(ThreatFindingNetFlow threatFindingNetFlow) {
        ThreatFinding threatFinding = threatFindingNetFlow.getThreatFinding();
        return "Source IP and port: "
                + threatFinding.getSourceAddress()
                + ":"
                + threatFinding.getSourcePort().toString()
                + "& Destination IP and port: "
                + threatFinding.getDestinationAddress()
                + ":"
                + threatFinding.getDestinationPort().toString();
    }

    default String netflowThreatCompleteDescription(ThreatFindingNetFlow threatFindingNetFlow) {
        ThreatFinding threatFinding = threatFindingNetFlow.getThreatFinding();
        return threatFindingNetFlow.getThreatLabel() + " "
                + threatFindingNetFlow.getThreatCategory()
                + " threat detected! Flagged as "
                + threatFinding.getFlag()
                + " on "
                + threatFinding.getProtocol()
                + " protocol. Start time: "
                + threatFinding.getTimeStart()
                + " End time: "
                + threatFinding.getTimeEnd()
                + " " + netflowThreatCompleteLocation(threatFindingNetFlow);
    }

    default String completeAnomalyLocation(TIAnomaly tiAnomaly) {
        NetFlowData anomalyData = tiAnomaly.getNetFlowData();
        return "Source IP and port: "
                + anomalyData.getSourceAddress()
                + ":"
                + anomalyData.getSourcePort().toString()
                + "& Destination IP and port: "
                + anomalyData.getDestinationAddress()
                + ":"
                + anomalyData.getDestinationPort().toString();

    }

    default String completeAnomalyDescription(TIAnomaly tiAnomaly) {
        NetFlowData anomalyData = tiAnomaly.getNetFlowData();
        return "Anomaly detected through "
                + tiAnomaly.getAnomalyDetectionMethod()
                + " method, with score "
                + tiAnomaly.getAnomalyScore()
                + ", on protocol "
                + anomalyData.getProtocol()
                + " with flag "
                + anomalyData.getFlag()
                + " . Start time: "
                + anomalyData.getTimeStart()
                + " End time: "
                + anomalyData.getTimeEnd()
                + " " + completeAnomalyLocation(tiAnomaly);
    }

}
