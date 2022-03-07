package eu.palantir.portal.kafka.processing;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import eu.palantir.portal.dto.message.ActionNotification;
import eu.palantir.portal.dto.message.IncidentNotification;
import eu.palantir.portal.dto.message.ir.IRPortalNotification;
import eu.palantir.portal.dto.message.rs.RSPortalNotification;
import eu.palantir.portal.dto.message.sm.SMResponse;
import eu.palantir.portal.dto.message.ti.TIAnomaly;
import eu.palantir.portal.dto.message.ti.ThreadFindingSysLog;
import eu.palantir.portal.dto.message.ti.ThreatFindingNetFlow;

@ApplicationScoped
public class PalantirNotificationsGatewayImpl implements PalantirNotificationsGateway {

    private static final Logger LOGGER = Logger.getLogger(PalantirNotificationsGatewayImpl.class);

    @Incoming("actions-notifications")
    @Override
    public void acceptActionsNotifications(ActionNotification actionNotification) {
        LOGGER.infof("Received %s", actionNotification);

        // TODO
    }

    @Incoming("incidents-notifications")
    @Override
    public void acceptIncidentNotifications(IncidentNotification incidentNotification) {
        LOGGER.infof("Received %s", incidentNotification);

        // TODO

    }

    @Incoming("ir-notify_portal")
    @Override
    public void acceptIRNotification(IRPortalNotification irPortalNotification) {
        LOGGER.infof("Received %s", irPortalNotification);

        // TODO

    }

    @Incoming("rs-notify_portal")
    @Override
    public void acceptRSNotification(RSPortalNotification rsPortalNotification) {
        LOGGER.infof("Received %s", rsPortalNotification);

        // TODO

    }

    @Incoming("cmr-responses")
    @Override
    public void acceptSMResponses(SMResponse smResponse) {
        LOGGER.infof("Received %s", smResponse);

        // TODO

    }

    @Incoming("ti-threat-findings-netflow")
    @Override
    public void acceptThreatFindingsNetFlow(ThreatFindingNetFlow threatFindingNetFlow) {
        LOGGER.infof("Received %s", threatFindingNetFlow);

        // TODO

    }

    @Incoming("ti-threat-findings-syslog")
    @Override
    public void acceptThreadFindingSysLog(ThreadFindingSysLog threadFindingSysLog) {
        LOGGER.infof("Received %s", threadFindingSysLog);

        // TODO

    }

    @Incoming("ti-anomaly-detection")
    @Override
    public void acceptTIAnomaly(TIAnomaly tiAnomaly) {
        LOGGER.infof("Received %s", tiAnomaly);

        // TODO

    }

}
