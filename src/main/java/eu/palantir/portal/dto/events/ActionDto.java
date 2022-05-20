package eu.palantir.portal.dto.events;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "componentType",
        "componentId",
        "componentIP",
        "targetIPs",
        "description",
        "createdTimestamp",
})
public class ActionDto implements Serializable {

    private Long id;

    private String componentType;

    private String componentId;

    private String componentIP;

    private List<String> targetIPs;

    private String name;

    private String description;

    private Instant createdTimestamp;

    public ActionDto() {
    }

    public ActionDto(Long id, String componentType, String componentId, String componentIP, List<String> targetIPs,
            String name, String description, Instant createdTimestamp) {
        this.id = id;
        this.componentType = componentType;
        this.componentId = componentId;
        this.componentIP = componentIP;
        this.targetIPs = targetIPs;
        this.name = name;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponentType() {
        return this.componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentId() {
        return this.componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getComponentIP() {
        return this.componentIP;
    }

    public void setComponentIP(String componentIP) {
        this.componentIP = componentIP;
    }

    public List<String> getTargetIPs() {
        return this.targetIPs;
    }

    public void setTargetIPs(List<String> targetIPs) {
        this.targetIPs = targetIPs;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    public void setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ActionDto)) {
            return false;
        }
        ActionDto actionDto = (ActionDto) o;
        return Objects.equals(id, actionDto.id) && Objects.equals(componentType, actionDto.componentType)
                && Objects.equals(componentId, actionDto.componentId)
                && Objects.equals(componentIP, actionDto.componentIP) && Objects.equals(targetIPs, actionDto.targetIPs)
                && Objects.equals(name, actionDto.name) && Objects.equals(description, actionDto.description)
                && Objects.equals(createdTimestamp, actionDto.createdTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, componentType, componentId, componentIP, targetIPs, name, description,
                createdTimestamp);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", componentType='" + getComponentType() + "'" +
                ", componentId='" + getComponentId() + "'" +
                ", componentIP='" + getComponentIP() + "'" +
                ", targetIPs='" + getTargetIPs() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", createdTimestamp='" + getCreatedTimestamp() + "'" +
                "}";
    }

}
