package eu.palantir.portal.kafka.processing;

import eu.palantir.portal.dto.message.ActionNotification;
import eu.palantir.portal.dto.message.IncidentNotification;
import eu.palantir.portal.dto.message.ir.IRPortalNotification;
import eu.palantir.portal.dto.message.rs.RSPortalNotification;
import eu.palantir.portal.dto.message.sm.SMResponse;
import eu.palantir.portal.dto.message.ti.TIAnomaly;
import eu.palantir.portal.dto.message.ti.ThreadFindingSysLog;
import eu.palantir.portal.dto.message.ti.ThreatFindingNetFlow;

public interface PalantirNotificationsGateway {

    void acceptActionsNotifications(ActionNotification actionNotification);

    void acceptIncidentNotifications(IncidentNotification incidentNotification);

    void acceptIRNotification(IRPortalNotification irPortalNotification);

    void acceptRSNotification(RSPortalNotification rsPortalNotification);

    void acceptSMResponses(SMResponse smResponse);

    void acceptThreatFindingsNetFlow(ThreatFindingNetFlow threatFindingNetFlow);

    void acceptThreadFindingSysLog(ThreadFindingSysLog threadFindingSysLog);

    void acceptTIAnomaly(TIAnomaly tiAnomaly);

}
