package eu.palantir.portal.model;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Incident extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private String sourceIpAddress;

    private String name;

    private String description;

    @CreationTimestamp
    private Instant createdTimestamp;

    public Incident() {
    }

    public Incident(Long id, String type, String sourceIpAddress, String name, String description,
            Instant createdTimestamp) {
        this.id = id;
        this.type = type;
        this.sourceIpAddress = sourceIpAddress;
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

    public String getSourceIpAddress() {
        return this.sourceIpAddress;
    }

    public void setSourceIpAddress(String sourceIpAddress) {
        this.sourceIpAddress = sourceIpAddress;
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

    public Incident id(Long id) {
        setId(id);
        return this;
    }

    public Incident type(String type) {
        setType(type);
        return this;
    }

    public Incident sourceIpAddress(String sourceIpAddress) {
        setSourceIpAddress(sourceIpAddress);
        return this;
    }

    public Incident name(String name) {
        setName(name);
        return this;
    }

    public Incident description(String description) {
        setDescription(description);
        return this;
    }

    public Incident createdTimestamp(Instant createdTimestamp) {
        setCreatedTimestamp(createdTimestamp);
        return this;
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
                && Objects.equals(sourceIpAddress, incident.sourceIpAddress) && Objects.equals(name, incident.name)
                && Objects.equals(description, incident.description)
                && Objects.equals(createdTimestamp, incident.createdTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, sourceIpAddress, name, description, createdTimestamp);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", type='" + getType() + "'" +
                ", sourceIpAddress='" + getSourceIpAddress() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", createdTimestamp='" + getCreatedTimestamp() + "'" +
                "}";
    }

}
