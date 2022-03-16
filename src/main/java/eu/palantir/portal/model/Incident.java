package eu.palantir.portal.model;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Incident extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private String location;

    private String name;

    private String description;

    // CHANGE add organization later.

    @CreationTimestamp
    private Instant createdTimestamp;

    public Incident() {
    }

    public Incident(Long id, String type, String sourceIpAddress, String name, String description,
            Instant createdTimestamp) {
        this.id = id;
        this.type = type;
        this.location = sourceIpAddress;
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
        if (!(o instanceof Incident)) {
            return false;
        }
        Incident incident = (Incident) o;
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
