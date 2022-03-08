package eu.palantir.portal.dto.message;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "incidentType",
        "detectedIncident",
        "incidentLocation",
        "incidentDescription"
})
public class IncidentNotification implements Serializable {

    @JsonProperty("incidentType")
    private String incidentType;
    @JsonProperty("detectedIncident")
    private String detectedIncident;
    @JsonProperty("incidentLocation")
    private String incidentLocation;
    @JsonProperty("incidentDescription")
    private String incidentDescription;

    public IncidentNotification() {
    }

    /**
     *
     * @param incidentType
     * @param detectedIncident
     * @param incidentDescription
     * @param incidentLocation
     */
    public IncidentNotification(String incidentType, String detectedIncident, String incidentLocation,
            String incidentDescription) {
        super();
        this.incidentType = incidentType;
        this.detectedIncident = detectedIncident;
        this.incidentLocation = incidentLocation;
        this.incidentDescription = incidentDescription;
    }

    @JsonProperty("incidentType")
    public String getIncidentType() {
        return incidentType;
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

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public void setDetectedIncident(String detectedIncident) {
        this.detectedIncident = detectedIncident;
    }

    public void setIncidentLocation(String incidentLocation) {
        this.incidentLocation = incidentLocation;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(IncidentNotification.class.getName()).append('@')
                .append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("incidentType");
        sb.append('=');
        sb.append(((this.incidentType == null) ? "<null>" : this.incidentType));
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
        result = ((result * 31) + ((this.incidentType == null) ? 0 : this.incidentType.hashCode()));
        result = ((result * 31) + ((this.detectedIncident == null) ? 0 : this.detectedIncident.hashCode()));
        result = ((result * 31) + ((this.incidentDescription == null) ? 0 : this.incidentDescription.hashCode()));
        result = ((result * 31) + ((this.incidentLocation == null) ? 0 : this.incidentLocation.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IncidentNotification) == false) {
            return false;
        }
        IncidentNotification rhs = ((IncidentNotification) other);
        return (((((this.incidentType == rhs.incidentType)
                || ((this.incidentType != null) && this.incidentType.equals(rhs.incidentType)))
                && ((this.detectedIncident == rhs.detectedIncident)
                        || ((this.detectedIncident != null) && this.detectedIncident.equals(rhs.detectedIncident))))
                && ((this.incidentDescription == rhs.incidentDescription) || ((this.incidentDescription != null)
                        && this.incidentDescription.equals(rhs.incidentDescription))))
                && ((this.incidentLocation == rhs.incidentLocation)
                        || ((this.incidentLocation != null) && this.incidentLocation.equals(rhs.incidentLocation))));
    }

}
