package eu.palantir.portal.dto.message;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "componentType",
        "componentId",
        "componentIP",
        "actionName",
        "actionDescription",
        "onIps"
})
public class ActionNotification implements Serializable {

    @JsonProperty("componentType")
    private String componentType;
    @JsonProperty("componentId")
    private String componentId;
    @JsonProperty("componentIP")
    private String componentIP;
    @JsonProperty("actionName")
    private String actionName;
    @JsonProperty("actionDescription")
    private String actionDescription;
    @JsonProperty("onIps")
    private List<String> onIps = null;

    public ActionNotification() {
    }

    /**
     *
     * @param componentType
     * @param onIps
     * @param componentId
     * @param actionDescription
     * @param componentIP
     * @param actionName
     */
    public ActionNotification(String componentType, String componentId, String componentIP, String actionName,
            String actionDescription, List<String> onIps) {
        super();
        this.componentType = componentType;
        this.componentId = componentId;
        this.componentIP = componentIP;
        this.actionName = actionName;
        this.actionDescription = actionDescription;
        this.onIps = onIps;
    }

    @JsonProperty("componentType")
    public String getComponentType() {
        return componentType;
    }

    @JsonProperty("componentId")
    public String getComponentId() {
        return componentId;
    }

    @JsonProperty("componentIP")
    public String getComponentIP() {
        return componentIP;
    }

    @JsonProperty("actionName")
    public String getActionName() {
        return actionName;
    }

    @JsonProperty("actionDescription")
    public String getActionDescription() {
        return actionDescription;
    }

    @JsonProperty("onIps")
    public List<String> getOnIps() {
        return onIps;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public void setComponentIP(String componentIP) {
        this.componentIP = componentIP;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public void setOnIps(List<String> onIps) {
        this.onIps = onIps;
    }

    @Override
    public String toString() {
        return "{" +
                " componentType='" + getComponentType() + "'" +
                ", componentId='" + getComponentId() + "'" +
                ", componentIP='" + getComponentIP() + "'" +
                ", actionName='" + getActionName() + "'" +
                ", actionDescription='" + getActionDescription() + "'" +
                ", onIps='" + getOnIps() + "'" +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.componentType == null) ? 0 : this.componentType.hashCode()));
        result = ((result * 31) + ((this.onIps == null) ? 0 : this.onIps.hashCode()));
        result = ((result * 31) + ((this.componentId == null) ? 0 : this.componentId.hashCode()));
        result = ((result * 31) + ((this.actionDescription == null) ? 0 : this.actionDescription.hashCode()));
        result = ((result * 31) + ((this.componentIP == null) ? 0 : this.componentIP.hashCode()));
        result = ((result * 31) + ((this.actionName == null) ? 0 : this.actionName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ActionNotification) == false) {
            return false;
        }
        ActionNotification rhs = ((ActionNotification) other);
        return (((((((this.componentType == rhs.componentType)
                || ((this.componentType != null) && this.componentType.equals(rhs.componentType)))
                && ((this.onIps == rhs.onIps) || ((this.onIps != null) && this.onIps.equals(rhs.onIps))))
                && ((this.componentId == rhs.componentId)
                        || ((this.componentId != null) && this.componentId.equals(rhs.componentId))))
                && ((this.actionDescription == rhs.actionDescription)
                        || ((this.actionDescription != null) && this.actionDescription.equals(rhs.actionDescription))))
                && ((this.componentIP == rhs.componentIP)
                        || ((this.componentIP != null) && this.componentIP.equals(rhs.componentIP))))
                && ((this.actionName == rhs.actionName)
                        || ((this.actionName != null) && this.actionName.equals(rhs.actionName))));
    }

}
