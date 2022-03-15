package eu.palantir.portal.rest;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import eu.palantir.portal.dto.events.ActionDto;
import eu.palantir.portal.dto.events.AttestationIncidentDto;
import eu.palantir.portal.dto.events.IncidentDto;
import eu.palantir.portal.dto.events.NetflowIncidentDto;
import eu.palantir.portal.dto.events.SyslogThreatIncidentDto;
import eu.palantir.portal.dto.mappers.ActionMapper;
import eu.palantir.portal.dto.mappers.IncidentMapper;
import eu.palantir.portal.model.Action;
import eu.palantir.portal.model.AttestationIncident;
import eu.palantir.portal.model.Incident;
import eu.palantir.portal.model.NetflowIncident;
import eu.palantir.portal.model.SyslogThreatIncident;
import io.smallrye.mutiny.Uni;

@Singleton
public class EventsResource {

    private static final Logger LOGGER = Logger.getLogger(AuthResource.class.getName());

    private final ActionMapper actionMapper;

    private final IncidentMapper incidentMapper;

    @Inject
    public EventsResource(ActionMapper actionMapper, IncidentMapper incidentMapper) {
        this.actionMapper = actionMapper;
        this.incidentMapper = incidentMapper;
    }

    @GET
    @Path("/action/{id}")
    public Uni<ActionDto> getAction(Long id) {
        return Action.<Action>findById(id).onItem().<ActionDto>transform(action -> actionMapper.toActionDto(action));
    }

    @GET
    @Path("/incident/{id}")
    public Uni<IncidentDto> getIncident(Long id) {
        return Incident.<Incident>findById(id).onItem().<IncidentDto>transform(
                incident -> incidentMapper.toIncidentDto(incident));
    }

    @GET
    @Path("/attestation_incident/{id}")
    public Uni<AttestationIncidentDto> getAttestationIncident(Long id) {
        return AttestationIncident.<AttestationIncident>findById(id).onItem().<AttestationIncidentDto>transform(
                incident -> incidentMapper.toAttestationIncidentDto(incident));
    }

    @GET
    @Path("/netflow_incident/{id}")
    public Uni<NetflowIncidentDto> getNetflowIncident(Long id) {
        return NetflowIncident.<NetflowIncident>findById(id).onItem().<NetflowIncidentDto>transform(
                incident -> incidentMapper.toNetflowIncidentDto(incident));
    }

    @GET
    @Path("/syslog_threat_incidents/{id}")
    public Uni<SyslogThreatIncidentDto> getSyslogThreatIncident(Long id) {
        return SyslogThreatIncident.<SyslogThreatIncident>findById(id).onItem().<SyslogThreatIncidentDto>transform(
                incident -> incidentMapper.toSyslogThreatIncidentDto(incident));
    }

}
