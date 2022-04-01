package eu.palantir.portal.kafka.processing;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import eu.palantir.portal.dto.mappers.ActionMapper;
import eu.palantir.portal.dto.mappers.IncidentMapper;
import eu.palantir.portal.dto.message.ActionNotification;
import eu.palantir.portal.dto.message.FrontendNotification;
import eu.palantir.portal.dto.message.IncidentNotification;
import eu.palantir.portal.dto.message.frontend.NotificationType;
import eu.palantir.portal.dto.message.ir.IRPortalNotification;
import eu.palantir.portal.dto.message.rs.RSPortalNotification;
import eu.palantir.portal.dto.message.sm.SMResponse;
import eu.palantir.portal.dto.message.ti.TIAnomaly;
import eu.palantir.portal.dto.message.ti.ThreadFindingSysLog;
import eu.palantir.portal.dto.message.ti.ThreatFindingNetFlow;
import eu.palantir.portal.model.Action;
import eu.palantir.portal.model.AttestationIncident;
import eu.palantir.portal.model.Incident;
import eu.palantir.portal.model.NetflowIncident;
import eu.palantir.portal.model.SyslogThreatIncident;
import eu.palantir.portal.websocket.FrontendNotificationsGateway;

@ApplicationScoped
public class PalantirNotificationsGatewayImpl implements PalantirNotificationsGateway {

    private static final Logger LOGGER = Logger.getLogger(PalantirNotificationsGatewayImpl.class);

    private final FrontendNotificationsGateway notificationsSocket;

    private final IncidentMapper incidentMapper;

    private final ActionMapper actionMapper;

    @Inject
    public PalantirNotificationsGatewayImpl(FrontendNotificationsGateway notificationsSocket,
            IncidentMapper incidentMapper, ActionMapper actionMapper) {
        this.notificationsSocket = notificationsSocket;
        this.incidentMapper = incidentMapper;
        this.actionMapper = actionMapper;
    }

    @Incoming("actions-notifications")
    @Transactional
    @Override
    public void acceptActionsNotifications(ActionNotification actionNotification) {
        LOGGER.infof("Received %s from actions-notifications", actionNotification);

        Action newAction = actionMapper.toAction(actionNotification);
        newAction.persist();

        if (newAction.getId() == null) {
            LOGGER.errorf("Failed to store entity for incoming action %s", newAction);
            return;
        }

        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.ACTION, "action",
                        newAction.getId().toString(), actionNotification, null, insertedEntityPath(
                                newAction.getId(), "action")));
    }

    @Incoming("incidents-notifications")
    @Transactional
    @Override
    public void acceptIncidentNotifications(IncidentNotification incidentNotification) {
        LOGGER.infof("Received %s from incidents-notifications", incidentNotification);

        Incident newIncident = incidentMapper.toIncident(incidentNotification);
        newIncident.persist();

        if (newIncident.getId() == null) {
            LOGGER.errorf("Failed to store entity for incoming generic incident %s", newIncident);
            return;
        }

        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.INCIDENT, "incident",
                        newIncident.getId().toString(), null, incidentNotification, insertedEntityPath(
                                newIncident.getId(), "incident")));

    }

    @Incoming("ir-notify_portal")
    @Transactional
    @Override
    public void acceptIRNotification(IRPortalNotification irPortalNotification) {
        LOGGER.infof("Received %s from ir-notify_portal", irPortalNotification);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(irPortalNotification);
        Incident newIncident = incidentMapper.toIncident(irPortalNotification);
        newIncident.persist();

        if (newIncident.getId() == null) {
            LOGGER.errorf("Failed to store entity for incoming IR Portal Notification %s", newIncident);
            return;
        }

        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.INCIDENT, "incident",
                        newIncident.getId().toString(), null, incidentNotification, insertedEntityPath(
                                newIncident.getId(), "incident")));
    }

    @Incoming("rs-notify_portal")
    @Transactional
    @Override
    public void acceptRSNotification(RSPortalNotification rsPortalNotification) {
        LOGGER.infof("Received %s from rs-notify_portal", rsPortalNotification);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(rsPortalNotification);
        AttestationIncident newIncident = incidentMapper.toAttestationIncident(rsPortalNotification);
        newIncident.persist();

        if (newIncident.getId() == null) {
            LOGGER.errorf("Failed to store entity for incoming RS PORTAL Notification %s", newIncident);
            return;
        }

        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.INCIDENT, "attestation_incident",
                        newIncident.getId().toString(), null, incidentNotification, insertedEntityPath(
                                newIncident.getId(), "attestation_incident")));

    }

    @Incoming("cmr-responses")
    @Transactional
    @Override
    public void acceptSMResponses(SMResponse smResponse) {
        LOGGER.infof("Received %s from cmr-responses", smResponse);

        // ADD LATER

    }

    @Incoming("ti-threat-findings-netflow")
    @Transactional
    @Override
    public void acceptThreatFindingsNetFlow(ThreatFindingNetFlow threatFindingNetFlow) {
        LOGGER.infof("Received %s from ti-threat-findings-netflow", threatFindingNetFlow);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(threatFindingNetFlow);
        NetflowIncident newIncident = incidentMapper.toNetflowIncident(threatFindingNetFlow);
        newIncident.persist();

        if (newIncident.getId() == null) {
            LOGGER.errorf("Failed to store entity for incoming Netflow Threat Finding %s", newIncident);
            return;
        }

        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.INCIDENT, "netflow_incident",
                        newIncident.getId().toString(), null, incidentNotification, insertedEntityPath(
                                newIncident.getId(), "netflow_incident")));
    }

    @Incoming("ti-threat-findings-syslog")
    @Transactional
    @Override
    public void acceptThreadFindingSysLog(ThreadFindingSysLog threadFindingSysLog) {
        LOGGER.infof("Received %s from ti-threat-findings-syslog", threadFindingSysLog);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(threadFindingSysLog);
        SyslogThreatIncident newIncident = incidentMapper.toSyslogThreatIncident(threadFindingSysLog);
        newIncident.persist();

        if (newIncident.getId() == null) {
            LOGGER.errorf("Failed to store entity for incoming SysLog Threat Finding %s", newIncident);
            return;
        }

        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.INCIDENT, "syslog_threat_incidents",
                        newIncident.getId().toString(), null, incidentNotification, insertedEntityPath(
                                newIncident.getId(), "syslog_threat_incidents")));
    }

    @Incoming("ti-anomaly-detection")
    @Transactional
    @Override
    public void acceptTIAnomaly(TIAnomaly tiAnomaly) {
        LOGGER.infof("Received %s from ti-anomaly-detection", tiAnomaly);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(tiAnomaly);

        NetflowIncident newIncident = incidentMapper.toNetflowIncident(tiAnomaly);
        newIncident.persist();

        if (newIncident.getId() == null) {
            LOGGER.errorf("Failed to store entity for incoming Anomaly detected by TI: %s", newIncident);
            return;
        }

        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.INCIDENT, "netflow_incident",
                        newIncident.getId().toString(), null, incidentNotification, insertedEntityPath(
                                newIncident.getId(), "netflow_incident")));
    }

    private URI insertedEntityPath(Long id, String collection) {
        return URI.create("/api/v1/events/" + collection + "/" + id);
    }

}
