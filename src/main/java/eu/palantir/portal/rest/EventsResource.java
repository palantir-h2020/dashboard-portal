package eu.palantir.portal.rest;

// import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import eu.palantir.portal.dto.events.ActionDto;
import eu.palantir.portal.dto.events.AttestationIncidentDto;
import eu.palantir.portal.dto.events.IncidentDto;
import eu.palantir.portal.dto.events.NetflowIncidentDto;
import eu.palantir.portal.dto.events.SyslogThreatIncidentDto;
import eu.palantir.portal.dto.mappers.ActionMapper;
import eu.palantir.portal.dto.mappers.IncidentMapper;
import eu.palantir.portal.dto.page.PageDto;
import eu.palantir.portal.model.Action;
import eu.palantir.portal.model.AttestationIncident;
import eu.palantir.portal.model.Incident;
import eu.palantir.portal.model.NetflowIncident;
import eu.palantir.portal.model.SyslogThreatIncident;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import org.jboss.logging.Logger;

import javax.transaction.Transactional;

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
    @Transactional
    public ActionDto getAction(@PathParam("id") Long id) {
        Action action = Action.findById(id);
        if (action == null) {
            return new ActionDto();
        } else {
            return actionMapper.toActionDto(action);
        }
    }

    @GET
    @Path("/action")
    @Transactional
    public PageDto<ActionDto> getActions(@QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("size") @DefaultValue("0") Integer size) {

        List<Action> actions;

        if (size > 0) {
            actions = Action
                    .<Action>findAll(Sort.by("createdTimestamp").descending())
                    .page(Page.of(index, size))
                    .list();
        } else {
            actions = Action.<Action>listAll(Sort.by("createdTimestamp").descending());
        }

        if (actions == null) {
            LOGGER.error("Query for all actions returned null!");
            return new PageDto<ActionDto>(new ArrayList<ActionDto>());
        } else {
            if (size == 0) {
                return new PageDto<ActionDto>(actionMapper.getActionDtos(actions));
            }
            return new PageDto<ActionDto>(actionMapper.getActionDtos(actions), Action.count(), index, size);
        }
    }

    @GET
    @Path("/incident/{id}")
    @Transactional
    public IncidentDto getIncident(@PathParam("id") Long id) {
        Incident incident = Incident.findById(id);
        if (incident == null) {
            return new IncidentDto();
        } else {
            return incidentMapper.toIncidentDto(incident);
        }
    }

    @GET
    @Path("/incident")
    @Transactional
    public PageDto<IncidentDto> getIncidents(
            @QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("size") @DefaultValue("0") Integer size) {

        List<Incident> incidents;

        if (size > 0) {
            incidents = Incident
                    .<Incident>findAll(Sort.by("createdTimestamp").descending())
                    .page(Page.of(index, size))
                    .list();
        } else {
            incidents = Incident.<Incident>listAll(Sort.by("createdTimestamp").descending());
        }

        if (incidents == null) {
            LOGGER.error("Query for all incidents returned null!");
            return new PageDto<IncidentDto>(new ArrayList<IncidentDto>());
        } else {
            if (size == 0) {
                return new PageDto<IncidentDto>(incidentMapper.getIncidentDtos(incidents));
            }
            return new PageDto<IncidentDto>(incidentMapper.getIncidentDtos(incidents), Incident.count(), index, size);
        }
    }

    @GET
    @Path("/attestation_incident/{id}")
    @Transactional
    public AttestationIncidentDto getAttestationIncident(@PathParam("id") Long id) {
        AttestationIncident incident = AttestationIncident.findById(id);
        if (incident == null) {
            return new AttestationIncidentDto();
        } else {
            return incidentMapper.toAttestationIncidentDto(incident);
        }
    }

    @GET
    @Path("/netflow_incident/{id}")
    @Transactional
    public NetflowIncidentDto getNetflowIncident(@PathParam("id") Long id) {
        NetflowIncident incident = NetflowIncident.findById(id);
        if (incident == null) {
            return new NetflowIncidentDto();
        } else {
            return incidentMapper.toNetflowIncidentDto(incident);
        }
    }

    @GET
    @Path("/syslog_threat_incidents/{id}")
    @Transactional
    public SyslogThreatIncidentDto getSyslogThreatIncident(@PathParam("id") Long id) {
        SyslogThreatIncident incident = SyslogThreatIncident.findById(id);
        if (incident == null) {
            return new SyslogThreatIncidentDto();
        } else {
            return incidentMapper.toSyslogThreatIncidentDto(incident);
        }
    }

}
