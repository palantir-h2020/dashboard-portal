package eu.palantir.portal.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Action extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private Long id;

    private String componentType;

    private String componentId;

    private String componentIP;

    private String targetIP;

    private String name;

    private String description;

    public Action() {
    }

    public Action(Long id, String componentType, String componentId, String componentIP, String targetIP, String name,
            String description) {
        this.id = id;
        this.componentType = componentType;
        this.componentId = componentId;
        this.componentIP = componentIP;
        this.targetIP = targetIP;
        this.name = name;
        this.description = description;
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

    public String getTargetIP() {
        return this.targetIP;
    }

    public void setTargetIP(String targetIP) {
        this.targetIP = targetIP;
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

    public Action id(Long id) {
        setId(id);
        return this;
    }

    public Action componentType(String componentType) {
        setComponentType(componentType);
        return this;
    }

    public Action componentId(String componentId) {
        setComponentId(componentId);
        return this;
    }

    public Action componentIP(String componentIP) {
        setComponentIP(componentIP);
        return this;
    }

    public Action targetIP(String targetIP) {
        setTargetIP(targetIP);
        return this;
    }

    public Action name(String name) {
        setName(name);
        return this;
    }

    public Action description(String description) {
        setDescription(description);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Action)) {
            return false;
        }
        Action action = (Action) o;
        return Objects.equals(id, action.id) && Objects.equals(componentType, action.componentType)
                && Objects.equals(componentId, action.componentId) && Objects.equals(componentIP, action.componentIP)
                && Objects.equals(targetIP, action.targetIP) && Objects.equals(name, action.name)
                && Objects.equals(description, action.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, componentType, componentId, componentIP, targetIP, name, description);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", componentType='" + getComponentType() + "'" +
                ", componentId='" + getComponentId() + "'" +
                ", componentIP='" + getComponentIP() + "'" +
                ", targetIP='" + getTargetIP() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                "}";
    }

}
