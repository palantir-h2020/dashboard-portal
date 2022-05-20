package eu.palantir.portal.dto.events;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IncidentDto implements Serializable {

    private Long id;

    private String type;

    private String location;

    private String name;

    private String description;

    private Instant createdTimestamp;

    public IncidentDto() {
    }

    public IncidentDto(Long id, String type, String location, String name, String description,
            Instant createdTimestamp) {
        this.id = id;
        this.type = type;
        this.location = location;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        if (!(o instanceof IncidentDto)) {
            return false;
        }
        IncidentDto incident = (IncidentDto) o;
        return Objects.equals(id, incident.id) && Objects.equals(type, incident.type)
                && Objects.equals(location, incident.location) && Objects.equals(name, incident.name)
                && Objects.equals(description, incident.description)
                && Objects.equals(createdTimestamp, incident.createdTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, location, name, description, createdTimestamp);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", type='" + getType() + "'" +
                ", location='" + getLocation() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", createdTimestamp='" + getCreatedTimestamp() + "'" +
                "}";
    }

}
