package eu.palantir.portal.dto.message;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import eu.palantir.portal.dto.message.frontend.NotificationType;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "collection",
        "id",
        "incident",
        "action",
        "path"
})
public class FrontendNotification implements Serializable {

    @JsonProperty("type")
    private NotificationType type;

    @JsonProperty("collection")
    private String collection;

    @JsonProperty("id")
    private String id;

    @JsonProperty("action")
    private ActionNotification action;

    @JsonProperty("incident")
    private IncidentNotification incident;

    @JsonProperty("path")
    private URI path;

    public FrontendNotification() {
    }

    public FrontendNotification(NotificationType type, String collection, String id, ActionNotification action,
            IncidentNotification incident, URI path) {
        this.type = type;
        this.collection = collection;
        this.id = id;
        this.action = action;
        this.incident = incident;
        this.path = path;
    }

    public NotificationType getType() {
        return this.type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getCollection() {
        return this.collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public URI getPath() {
        return this.path;
    }

    public void setPath(URI path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FrontendNotification)) {
            return false;
        }
        FrontendNotification frontendNotification = (FrontendNotification) o;
        return Objects.equals(type, frontendNotification.type)
                && Objects.equals(collection, frontendNotification.collection)
                && Objects.equals(id, frontendNotification.id) && Objects.equals(action, frontendNotification.action)
                && Objects.equals(incident, frontendNotification.incident)
                && Objects.equals(path, frontendNotification.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, collection, id, action, incident, path);
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", collection='" + getCollection() + "'" +
                ", id='" + getId() + "'" +
                ", action='" + getAction() + "'" +
                ", incident='" + getIncident() + "'" +
                ", path='" + getPath() + "'" +
                "}";
    }

}
