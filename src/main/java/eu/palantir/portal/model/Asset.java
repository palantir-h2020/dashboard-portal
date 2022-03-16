package eu.palantir.portal.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
public class Asset extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;

    private String description;

    private String ip;

    private String port;

    @ManyToOne
    @JoinColumn(name = "organizationId", nullable = false)
    private Organization organization;

    public Asset() {
    }

    public Asset(Long id, String name, String type, String description, String ip, String port,
            Organization organization) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.ip = ip;
        this.port = port;
        this.organization = organization;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Asset id(Long id) {
        setId(id);
        return this;
    }

    public Asset name(String name) {
        setName(name);
        return this;
    }

    public Asset type(String type) {
        setType(type);
        return this;
    }

    public Asset description(String description) {
        setDescription(description);
        return this;
    }

    public Asset ip(String ip) {
        setIp(ip);
        return this;
    }

    public Asset port(String port) {
        setPort(port);
        return this;
    }

    public Asset organization(Organization organization) {
        setOrganization(organization);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Asset)) {
            return false;
        }
        Asset asset = (Asset) o;
        return Objects.equals(id, asset.id) && Objects.equals(name, asset.name) && Objects.equals(type, asset.type)
                && Objects.equals(description, asset.description) && Objects.equals(ip, asset.ip)
                && Objects.equals(port, asset.port) && Objects.equals(organization, asset.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, description, ip, port, organization);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", type='" + getType() + "'" +
                ", description='" + getDescription() + "'" +
                ", ip='" + getIp() + "'" +
                ", port='" + getPort() + "'" +
                ", organization='" + getOrganization() + "'" +
                "}";
    }

}
