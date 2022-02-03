package eu.palantir.portal.dto.message.ir;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "detectedIncident",
        "incidentLocation",
        "incidentDescription"
})
public class IRPortalNotification implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("detectedIncident")
    private String detectedIncident;
    @JsonProperty("incidentLocation")
    private String incidentLocation;
    @JsonProperty("incidentDescription")
    private String incidentDescription;
    private final static long serialVersionUID = 5794950001150088660L;

    /**
     * No args constructor for use in serialization
     *
     */
    public IRPortalNotification() {
    }

    /**
     *
     * @param detectedIncident
     * @param message
     * @param incidentDescription
     * @param incidentLocation
     */
    public IRPortalNotification(String message, String detectedIncident, String incidentLocation,
            String incidentDescription) {
        super();
        this.message = message;
        this.detectedIncident = detectedIncident;
        this.incidentLocation = incidentLocation;
        this.incidentDescription = incidentDescription;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("detectedIncident")
    public String getDetectedIncident() {
        return detectedIncident;
    }

    @JsonProperty("incidentLocation")
    public String getIncidentLocation() {
        return incidentLocation;
    }

    @JsonProperty("incidentDescription")
    public String getIncidentDescription() {
        return incidentDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IRPortalNotification.class.getName()).append('@')
                .append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null) ? "<null>" : this.message));
        sb.append(',');
        sb.append("detectedIncident");
        sb.append('=');
        sb.append(((this.detectedIncident == null) ? "<null>" : this.detectedIncident));
        sb.append(',');
        sb.append("incidentLocation");
        sb.append('=');
        sb.append(((this.incidentLocation == null) ? "<null>" : this.incidentLocation));
        sb.append(',');
        sb.append("incidentDescription");
        sb.append('=');
        sb.append(((this.incidentDescription == null) ? "<null>" : this.incidentDescription));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.detectedIncident == null) ? 0 : this.detectedIncident.hashCode()));
        result = ((result * 31) + ((this.message == null) ? 0 : this.message.hashCode()));
        result = ((result * 31) + ((this.incidentDescription == null) ? 0 : this.incidentDescription.hashCode()));
        result = ((result * 31) + ((this.incidentLocation == null) ? 0 : this.incidentLocation.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IRPortalNotification) == false) {
            return false;
        }
        IRPortalNotification rhs = ((IRPortalNotification) other);
        return (((((this.detectedIncident == rhs.detectedIncident)
                || ((this.detectedIncident != null) && this.detectedIncident.equals(rhs.detectedIncident)))
                && ((this.message == rhs.message) || ((this.message != null) && this.message.equals(rhs.message))))
                && ((this.incidentDescription == rhs.incidentDescription) || ((this.incidentDescription != null)
                        && this.incidentDescription.equals(rhs.incidentDescription))))
                && ((this.incidentLocation == rhs.incidentLocation)
                        || ((this.incidentLocation != null) && this.incidentLocation.equals(rhs.incidentLocation))));
    }

}