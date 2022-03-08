package eu.palantir.portal.kafka.processing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

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
import eu.palantir.portal.websocket.NotificationsSocket;

@ApplicationScoped
public class PalantirNotificationsGatewayImpl implements PalantirNotificationsGateway {

    private static final Logger LOGGER = Logger.getLogger(PalantirNotificationsGatewayImpl.class);

    private final NotificationsSocket notificationsSocket;

    private final IncidentMapper incidentMapper;

    @Inject
    public PalantirNotificationsGatewayImpl(NotificationsSocket notificationsSocket, IncidentMapper incidentMapper) {
        this.notificationsSocket = notificationsSocket;
        this.incidentMapper = incidentMapper;
    }

    @Incoming("actions-notifications")
    @Override
    public void acceptActionsNotifications(ActionNotification actionNotification) {
        LOGGER.infof("Received %s from actions-notifications", actionNotification);

        // TODO CHANGE: Persistence
        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.ACTION, actionNotification, null));
    }

    @Incoming("incidents-notifications")
    @Override
    public void acceptIncidentNotifications(IncidentNotification incidentNotification) {
        LOGGER.infof("Received %s from incidents-notifications", incidentNotification);

        // TODO CHANGE: Persistence
        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.NOTIFICATION, null, incidentNotification));

    }

    @Incoming("ir-notify_portal")
    @Override
    public void acceptIRNotification(IRPortalNotification irPortalNotification) {
        LOGGER.infof("Received %s from ir-notify_portal", irPortalNotification);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(irPortalNotification);

        // TODO CHANGE: Persistence
        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.NOTIFICATION, null, incidentNotification));
    }

    @Incoming("rs-notify_portal")
    @Override
    public void acceptRSNotification(RSPortalNotification rsPortalNotification) {
        LOGGER.infof("Received %s from rs-notify_portal", rsPortalNotification);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(rsPortalNotification);

        // TODO CHANGE: Persistence
        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.NOTIFICATION, null, incidentNotification));

    }

    @Incoming("cmr-responses")
    @Override
    public void acceptSMResponses(SMResponse smResponse) {
        LOGGER.infof("Received %s from cmr-responses", smResponse);

        // TODO

    }

    @Incoming("ti-threat-findings-netflow")
    @Override
    public void acceptThreatFindingsNetFlow(ThreatFindingNetFlow threatFindingNetFlow) {
        LOGGER.infof("Received %s from ti-threat-findings-netflow", threatFindingNetFlow);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(threatFindingNetFlow);

        // TODO CHANGE: Persistence
        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.NOTIFICATION, null, incidentNotification));
    }

    @Incoming("ti-threat-findings-syslog")
    @Override
    public void acceptThreadFindingSysLog(ThreadFindingSysLog threadFindingSysLog) {
        LOGGER.infof("Received %s from ti-threat-findings-syslog", threadFindingSysLog);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(threadFindingSysLog);

        // TODO CHANGE: Persistence
        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.NOTIFICATION, null, incidentNotification));
    }

    @Incoming("ti-anomaly-detection")
    @Override
    public void acceptTIAnomaly(TIAnomaly tiAnomaly) {
        LOGGER.infof("Received %s from ti-anomaly-detection", tiAnomaly);
        IncidentNotification incidentNotification = incidentMapper.toIncidentNotification(tiAnomaly);

        // TODO CHANGE: Persistence
        // ADD LATER: User filtering
        notificationsSocket
                .sendNotification(new FrontendNotification(NotificationType.NOTIFICATION, null, incidentNotification));
    }

}
