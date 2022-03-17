package eu.palantir.portal.kafka.processing;

import java.net.URI;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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
import io.quarkus.hibernate.reactive.panache.Panache;

@ApplicationScoped
public class PalantirNotificationsGatewayImpl implements PalantirNotificationsGateway {

    private static final Logger LOGGER = Logger.getLogger(PalantirNotificationsGatewayImpl.class);

    private static final Consumer<Throwable> logStorageErrorLambda = (failure) -> {
        LOGGER.errorf("Failed to store entity for incoming event, with: %s", failure);
    };

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
    @Override
    public void acceptActionsNotifications(ActionNotification actionNotification) {
        LOGGER.infof("Received %s from actions-notifications", actionNotification);

        Action newAction = actionMapper.toAction(actionNotification);

        Panache.<Action>withTransaction(newAction::persist)
                .subscribe().with(
                        inserted -> {

                            // ADD LATER: User filtering
                            notificationsSocket
                                    .sendNotification(new FrontendNotification(NotificationType.ACTION, "action",
                                            inserted.getId().toString(), actionNotification, null, insertedEntityPath(
                                                    inserted.getId(), "action")));
                        }, logStorageErrorLambda);
    }

    @Incoming("incidents-notifications")
    @Override
    public void acceptIncidentNotifications(IncidentNotification incidentNotification) {
        LOGGER.infof("Received %s from incidents-notifications", incidentNotification);

        Incident newIncident = incidentMapper.toIncident(incidentNotification);

        Panache.<Incident>withTransaction(
                newIncident::persist)
                .subscribe().with(
                        inserted -> {

                            // ADD LATER: User filtering
                            notificationsSocket
                                    .sendNotification(new FrontendNotification(NotificationType.INCIDENT, "incident",
                                            inserted.getId().toString(), null, incidentNotification, insertedEntityPath(
                                                    inserted.getId(), "incident")));
                        }, logStorageErrorLambda);

    }

    @Incoming("ir-notify_portal")
    @Override
    public void acceptIRNotification(IRPortalNotification irPortalNotification) {
        LOGGER.infof("Received %s from ir-notify_portal", irPortalNotification);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(irPortalNotification);
        Incident newIncident = incidentMapper.toIncident(irPortalNotification);

        Panache.<Incident>withTransaction(
                newIncident::persist)
                .subscribe().with(
                        inserted -> {

                            // ADD LATER: User filtering
                            notificationsSocket
                                    .sendNotification(new FrontendNotification(NotificationType.INCIDENT, "incident",
                                            inserted.getId().toString(), null, incidentNotification, insertedEntityPath(
                                                    inserted.getId(), "incident")));
                        }, logStorageErrorLambda);
    }

    @Incoming("rs-notify_portal")
    @Override
    public void acceptRSNotification(RSPortalNotification rsPortalNotification) {
        LOGGER.infof("Received %s from rs-notify_portal", rsPortalNotification);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(rsPortalNotification);
        AttestationIncident newIncident = incidentMapper.toAttestationIncident(rsPortalNotification);

        Panache.<AttestationIncident>withTransaction(
                newIncident::persist)
                .subscribe().with(
                        inserted -> {

                            // ADD LATER: User filtering
                            notificationsSocket
                                    .sendNotification(
                                            new FrontendNotification(NotificationType.INCIDENT, "attestation_incident",
                                                    inserted.getId().toString(), null, incidentNotification,
                                                    insertedEntityPath(
                                                            inserted.getId(), "attestation_incident")));
                        }, logStorageErrorLambda);

    }

    @Incoming("cmr-responses")
    @Override
    public void acceptSMResponses(SMResponse smResponse) {
        LOGGER.infof("Received %s from cmr-responses", smResponse);

        // ADD LATER

    }

    @Incoming("ti-threat-findings-netflow")
    @Override
    public void acceptThreatFindingsNetFlow(ThreatFindingNetFlow threatFindingNetFlow) {
        LOGGER.infof("Received %s from ti-threat-findings-netflow", threatFindingNetFlow);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(threatFindingNetFlow);
        NetflowIncident newIncident = incidentMapper.toNetflowIncident(threatFindingNetFlow);

        Panache.<NetflowIncident>withTransaction(
                newIncident::persist)
                .subscribe().with(
                        inserted -> {

                            // ADD LATER: User filtering
                            notificationsSocket
                                    .sendNotification(
                                            new FrontendNotification(NotificationType.INCIDENT, "netflow_incident",
                                                    inserted.getId().toString(), null, incidentNotification,
                                                    insertedEntityPath(
                                                            inserted.getId(), "netflow_incident")));
                        }, logStorageErrorLambda);
    }

    @Incoming("ti-threat-findings-syslog")
    @Override
    public void acceptThreadFindingSysLog(ThreadFindingSysLog threadFindingSysLog) {
        LOGGER.infof("Received %s from ti-threat-findings-syslog", threadFindingSysLog);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(threadFindingSysLog);
        SyslogThreatIncident newIncident = incidentMapper.toSyslogThreatIncident(threadFindingSysLog);

        Panache.<SyslogThreatIncident>withTransaction(
                newIncident::persist)
                .subscribe().with(
                        inserted -> {

                            // ADD LATER: User filtering
                            notificationsSocket
                                    .sendNotification(new FrontendNotification(NotificationType.INCIDENT,
                                            "syslog_threat_incidents",
                                            inserted.getId().toString(), null, incidentNotification,
                                            insertedEntityPath(
                                                    inserted.getId(), "syslog_threat_incidents")));
                        }, logStorageErrorLambda);
    }

    @Incoming("ti-anomaly-detection")
    @Override
    public void acceptTIAnomaly(TIAnomaly tiAnomaly) {
        LOGGER.infof("Received %s from ti-anomaly-detection", tiAnomaly);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(tiAnomaly);

        NetflowIncident newIncident = incidentMapper.toNetflowIncident(tiAnomaly);

        Panache.<NetflowIncident>withTransaction(
                newIncident::persist)
                .subscribe().with(
                        inserted -> {

                            // ADD LATER: User filtering
                            notificationsSocket
                                    .sendNotification(
                                            new FrontendNotification(NotificationType.INCIDENT, "netflow_incident",
                                                    inserted.getId().toString(), null, incidentNotification,
                                                    insertedEntityPath(
                                                            inserted.getId(), "netflow_incident")));
                        }, logStorageErrorLambda);
    }

    private URI insertedEntityPath(Long id, String collection) {
        return URI.create("/api/v1/events/" + collection + "/" + id);
    }

}
