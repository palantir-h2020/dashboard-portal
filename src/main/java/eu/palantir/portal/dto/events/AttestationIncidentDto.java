package eu.palantir.portal.dto.events;

import java.time.Instant;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttestationIncidentDto extends IncidentDto {

    private String attestationInfo;

    private String attestationInstance;

    private String nodeType;

    public AttestationIncidentDto() {
    }

    public AttestationIncidentDto(Long id, String type, String location, String name, String description,
            Instant createdTimestamp, String attestationInfo, String attestationInstance, String nodeType) {
        this.setId(id);
        this.setType(type);
        this.setLocation(location);
        this.setName(name);
        this.setDescription(description);
        this.setCreatedTimestamp(createdTimestamp);
        this.attestationInfo = attestationInfo;
        this.attestationInstance = attestationInstance;
        this.nodeType = nodeType;
    }

    public AttestationIncidentDto(String attestationInfo, String attestationInstance, String nodeType) {
        this.attestationInfo = attestationInfo;
        this.attestationInstance = attestationInstance;
        this.nodeType = nodeType;
    }

    public String getAttestationInfo() {
        return this.attestationInfo;
    }

    public void setAttestationInfo(String attestationInfo) {
        this.attestationInfo = attestationInfo;
    }

    public String getAttestationInstance() {
        return this.attestationInstance;
    }

    public void setAttestationInstance(String attestationInstance) {
        this.attestationInstance = attestationInstance;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AttestationIncidentDto)) {
            return false;
        }
        AttestationIncidentDto attestationIncident = (AttestationIncidentDto) o;
        return super.equals((IncidentDto) o)
                && Objects.equals(attestationInfo, attestationIncident.attestationInfo)
                && Objects.equals(attestationInstance, attestationIncident.attestationInstance)
                && Objects.equals(nodeType, attestationIncident.nodeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attestationInfo, attestationInstance, nodeType);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() - 1) +
                " attestationInfo='" + getAttestationInfo() + "'" +
                ", attestationInstance='" + getAttestationInstance() + "'" +
                ", nodeType='" + getNodeType() + "'" +
                "}";
    }

}
