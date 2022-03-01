package eu.palantir.portal.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.palantir.portal.util.IpUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "org_ip_seq_range")
public class IPrange extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private String start;

    private String end;

    @ManyToOne
    @JoinColumn(name = "organizationId", nullable = false)
    private Organization organization;

    public IPrange() {
    }

    public IPrange(Long id, String start, String end, Organization organization) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.organization = organization;
    }

    public boolean checkIPIsInRange(String ip) {
        return IpUtil.checkIPIsInSeqRange(ip, this.start, this.end);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public IPrange id(Long id) {
        setId(id);
        return this;
    }

    public IPrange start(String start) {
        setStart(start);
        return this;
    }

    public IPrange end(String end) {
        setEnd(end);
        return this;
    }

    public IPrange organization(Organization organization) {
        setOrganization(organization);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof IPrange)) {
            return false;
        }
        IPrange iPrange = (IPrange) o;
        return Objects.equals(id, iPrange.id) && Objects.equals(start, iPrange.start)
                && Objects.equals(end, iPrange.end) && Objects.equals(organization, iPrange.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, organization);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", start='" + getStart() + "'" +
                ", end='" + getEnd() + "'" +
                ", organization='" + getOrganization() + "'" +
                "}";
    }

}
