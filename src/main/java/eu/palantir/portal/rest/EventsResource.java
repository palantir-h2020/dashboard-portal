package eu.palantir.portal.rest;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

@Singleton
public class EventsResource {

    private static final Logger LOGGER = Logger.getLogger(EventsResource.class);

    private final ActionMapper actionMapper;

    private final IncidentMapper incidentMapper;

    @Inject
    public EventsResource(ActionMapper actionMapper, IncidentMapper incidentMapper) {
        this.actionMapper = actionMapper;
        this.incidentMapper = incidentMapper;
    }

    @GET
    @Path("/action/{id}")
    @ReactiveTransactional
    public Uni<ActionDto> getAction(@PathParam("id") Long id) {
        return Action.<Action>findById(id).onItem().<ActionDto>transform(action -> actionMapper.toActionDto(action));
    }

    @GET
    @Path("/incident/{id}")
    @ReactiveTransactional
    public Uni<IncidentDto> getIncident(@PathParam("id") Long id) {
        return Incident.<Incident>findById(id).onItem().<IncidentDto>transform(
                incident -> incidentMapper.toIncidentDto(incident));
    }

    @GET
    @Path("/attestation_incident/{id}")
    @ReactiveTransactional
    public Uni<AttestationIncidentDto> getAttestationIncident(@PathParam("id") Long id) {
        return AttestationIncident.<AttestationIncident>findById(id).onItem().<AttestationIncidentDto>transform(
                incident -> incidentMapper.toAttestationIncidentDto(incident));
    }

    @GET
    @Path("/netflow_incident/{id}")
    @ReactiveTransactional
    public Uni<NetflowIncidentDto> getNetflowIncident(@PathParam("id") Long id) {
        return NetflowIncident.<NetflowIncident>findById(id).onItem().<NetflowIncidentDto>transform(
                incident -> incidentMapper.toNetflowIncidentDto(incident));
    }

    @GET
    @Path("/syslog_threat_incidents/{id}")
    @ReactiveTransactional
    public Uni<SyslogThreatIncidentDto> getSyslogThreatIncident(@PathParam("id") Long id) {
        LOGGER.infof("Received this: %s", id); // TEST
        return SyslogThreatIncident.<SyslogThreatIncident>findById(id).onItem().<SyslogThreatIncidentDto>transform(
                incident -> incidentMapper.toSyslogThreatIncidentDto(incident));
    }

}
