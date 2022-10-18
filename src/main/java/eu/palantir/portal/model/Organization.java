package eu.palantir.portal.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// This is the subscriber to the PALANTIR platform.
// The SC developer can also be part of an organization.
@Entity
public class Organization extends PanacheEntityBase {

    @Id
    // Corresponds to the VAT number or something similarly unique.
    private String id;

    @NotNull
    private String name; // Label

    @NotNull
    // IP or URI of the PALANTIR "control plane" reverse proxy.
    private String controlProxy;

    @NotNull
    // One deployment model active at a time for each organization.
    private PalantirDeploymentModel deploymentModel;

    @NotNull
    @ElementCollection
    @CollectionTable(name = "ORG_SECURITY_CAPABILITY_SUBS", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
    @Column(name = "security_capability")
    // List of subscribed SC IDs.
    private List<String> securityCapabilitySubscriptions = new ArrayList<String>();

    @CreationTimestamp
    private Instant createdTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControlProxy() {
        return this.controlProxy;
    }

    public void setControlProxy(String controlProxy) {
        this.controlProxy = controlProxy;
    }

    public PalantirDeploymentModel getDeploymentModel() {
        return this.deploymentModel;
    }

    public void setDeploymentModel(PalantirDeploymentModel deploymentModel) {
        this.deploymentModel = deploymentModel;
    }

    public List<String> getSecurityCapabilitySubscriptions() {
        return this.securityCapabilitySubscriptions;
    }

    public void setSecurityCapabilitySubscriptions(List<String> securityCapabilitySubscriptions) {
        this.securityCapabilitySubscriptions = securityCapabilitySubscriptions;
    }

    public Instant getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    public void setCreatedTimestamp(Instant createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Instant getUpdatedTimestamp() {
        return this.updatedTimestamp;
    }

    public void setUpdatedTimestamp(Instant updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
