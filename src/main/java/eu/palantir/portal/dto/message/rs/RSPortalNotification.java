package eu.palantir.portal.dto.message.rs;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "serviceIP",
        "nodeType",
        "nodeId",
        "failedAttestation",
        "failedDescription"
})
public class RSPortalNotification implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("serviceIP")
    private String serviceIP;
    @JsonProperty("nodeType")
    private String nodeType;
    @JsonProperty("nodeId")
    private String nodeId;
    @JsonProperty("failedAttestation")
    private String failedAttestation;
    @JsonProperty("failedDescription")
    private String failedDescription;

    public RSPortalNotification() {
    }

    /**
     *
     * @param serviceIP
     * @param failedAttestation
     * @param failedDescription
     * @param message
     * @param nodeType
     * @param nodeId
     */
    public RSPortalNotification(String message, String serviceIP, String nodeType, String nodeId,
            String failedAttestation, String failedDescription) {
        super();
        this.message = message;
        this.serviceIP = serviceIP;
        this.nodeType = nodeType;
        this.nodeId = nodeId;
        this.failedAttestation = failedAttestation;
        this.failedDescription = failedDescription;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("serviceIP")
    public String getServiceIP() {
        return serviceIP;
    }

    @JsonProperty("nodeType")
    public String getNodeType() {
        return nodeType;
    }

    @JsonProperty("nodeId")
    public String getNodeId() {
        return nodeId;
    }

    @JsonProperty("failedAttestation")
    public String getFailedAttestation() {
        return failedAttestation;
    }

    @JsonProperty("failedDescription")
    public String getFailedDescription() {
        return failedDescription;
    }

    @Override
    public String toString() {
        return "{" +
                " message='" + getMessage() + "'" +
                ", serviceIP='" + getServiceIP() + "'" +
                ", nodeType='" + getNodeType() + "'" +
                ", nodeId='" + getNodeId() + "'" +
                ", failedAttestation='" + getFailedAttestation() + "'" +
                ", failedDescription='" + getFailedDescription() + "'" +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.serviceIP == null) ? 0 : this.serviceIP.hashCode()));
        result = ((result * 31) + ((this.failedAttestation == null) ? 0 : this.failedAttestation.hashCode()));
        result = ((result * 31) + ((this.failedDescription == null) ? 0 : this.failedDescription.hashCode()));
        result = ((result * 31) + ((this.message == null) ? 0 : this.message.hashCode()));
        result = ((result * 31) + ((this.nodeType == null) ? 0 : this.nodeType.hashCode()));
        result = ((result * 31) + ((this.nodeId == null) ? 0 : this.nodeId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RSPortalNotification) == false) {
            return false;
        }
        RSPortalNotification rhs = ((RSPortalNotification) other);
        return (((((((this.serviceIP == rhs.serviceIP)
                || ((this.serviceIP != null) && this.serviceIP.equals(rhs.serviceIP)))
                && ((this.failedAttestation == rhs.failedAttestation)
                        || ((this.failedAttestation != null) && this.failedAttestation.equals(rhs.failedAttestation))))
                && ((this.failedDescription == rhs.failedDescription)
                        || ((this.failedDescription != null) && this.failedDescription.equals(rhs.failedDescription))))
                && ((this.message == rhs.message) || ((this.message != null) && this.message.equals(rhs.message))))
                && ((this.nodeType == rhs.nodeType) || ((this.nodeType != null) && this.nodeType.equals(rhs.nodeType))))
                && ((this.nodeId == rhs.nodeId) || ((this.nodeId != null) && this.nodeId.equals(rhs.nodeId))));
    }

}
