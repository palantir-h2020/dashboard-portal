package eu.palantir.portal.dto.message;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import eu.palantir.portal.dto.message.frontend.NotificationType;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "incident",
        "action",
})
public class FrontendNotification {

    @JsonProperty("type")
    private NotificationType type;

    @JsonProperty("action")
    private ActionNotification action;

    @JsonProperty("incident")
    private IncidentNotification incident;

    public FrontendNotification() {
    }

    public FrontendNotification(NotificationType type, ActionNotification action, IncidentNotification incident) {
        this.type = type;
        this.action = action;
        this.incident = incident;
    }

    public NotificationType getType() {
        return this.type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public ActionNotification getAction() {
        return this.action;
    }

    public void setAction(ActionNotification action) {
        this.action = action;
    }

    public IncidentNotification getIncident() {
        return this.incident;
    }

    public void setIncident(IncidentNotification incident) {
        this.incident = incident;
    }

    public FrontendNotification type(NotificationType type) {
        setType(type);
        return this;
    }

    public FrontendNotification action(ActionNotification action) {
        setAction(action);
        return this;
    }

    public FrontendNotification incident(IncidentNotification incident) {
        setIncident(incident);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FrontendNotification)) {
            return false;
        }
        FrontendNotification frontendNotification = (FrontendNotification) o;
        return Objects.equals(type, frontendNotification.type) && Objects.equals(action, frontendNotification.action)
                && Objects.equals(incident, frontendNotification.incident);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, action, incident);
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", action='" + getAction() + "'" +
                ", incident='" + getIncident() + "'" +
                "}";
    }

}
