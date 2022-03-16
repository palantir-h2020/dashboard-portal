package eu.palantir.portal.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.palantir.portal.util.IpUtil;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Table(name = "org_ip_subnet_range")
public class IPsubnet extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private String subnetString;

    private Integer prefixLength;

    @ManyToOne
    @JoinColumn(name = "organizationId", nullable = false)
    private Organization organization;

    public IPsubnet() {
    }

    public IPsubnet(Long id, String subnetString, Integer prefixLength, Organization organization) {
        this.id = id;
        this.subnetString = subnetString;
        this.prefixLength = prefixLength;
        this.organization = organization;
    }

    public boolean checkIPIsInSubnet(String checkedIp) {
        return IpUtil.checkIPIsInSubnet(checkedIp, this.subnetString, this.prefixLength);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubnetString() {
        return this.subnetString;
    }

    public void setSubnetString(String subnetString) {
        this.subnetString = subnetString;
    }

    public Integer getPrefixLength() {
        return this.prefixLength;
    }

    public void setPrefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public IPsubnet id(Long id) {
        setId(id);
        return this;
    }

    public IPsubnet subnetString(String subnetString) {
        setSubnetString(subnetString);
        return this;
    }

    public IPsubnet prefixLength(Integer prefixLength) {
        setPrefixLength(prefixLength);
        return this;
    }

    public IPsubnet organization(Organization organization) {
        setOrganization(organization);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof IPsubnet)) {
            return false;
        }
        IPsubnet iPsubnet = (IPsubnet) o;
        return Objects.equals(id, iPsubnet.id) && Objects.equals(subnetString, iPsubnet.subnetString)
                && Objects.equals(prefixLength, iPsubnet.prefixLength)
                && Objects.equals(organization, iPsubnet.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subnetString, prefixLength, organization);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", subnetString='" + getSubnetString() + "'" +
                ", prefixLength='" + getPrefixLength() + "'" +
                ", organization='" + getOrganization() + "'" +
                "}";
    }

}
