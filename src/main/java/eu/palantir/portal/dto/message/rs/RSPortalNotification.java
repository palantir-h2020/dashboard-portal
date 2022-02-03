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
    private final static long serialVersionUID = -6620096027865071907L;

    /**
     * No args constructor for use in serialization
     *
     */
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
        StringBuilder sb = new StringBuilder();
        sb.append(RSPortalNotification.class.getName()).append('@')
                .append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null) ? "<null>" : this.message));
        sb.append(',');
        sb.append("serviceIP");
        sb.append('=');
        sb.append(((this.serviceIP == null) ? "<null>" : this.serviceIP));
        sb.append(',');
        sb.append("nodeType");
        sb.append('=');
        sb.append(((this.nodeType == null) ? "<null>" : this.nodeType));
        sb.append(',');
        sb.append("nodeId");
        sb.append('=');
        sb.append(((this.nodeId == null) ? "<null>" : this.nodeId));
        sb.append(',');
        sb.append("failedAttestation");
        sb.append('=');
        sb.append(((this.failedAttestation == null) ? "<null>" : this.failedAttestation));
        sb.append(',');
        sb.append("failedDescription");
        sb.append('=');
        sb.append(((this.failedDescription == null) ? "<null>" : this.failedDescription));
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