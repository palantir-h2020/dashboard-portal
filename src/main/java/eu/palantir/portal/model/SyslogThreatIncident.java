package eu.palantir.portal.model;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;

@Entity
public class SyslogThreatIncident extends Incident {

    private String classificationConfidence;

    private String outlierScore;

    private String sourceIpAddress;

    public SyslogThreatIncident() {
    }

    public SyslogThreatIncident(Long id, String type, String location, String name, String description,
            Instant createdTimestamp, String sourceIpAddress, String classificationConfidence, String outlierScore) {
        this.setId(id);
        this.setType(type);
        this.setLocation(location);
        this.setName(name);
        this.setDescription(description);
        this.setCreatedTimestamp(createdTimestamp);
        this.sourceIpAddress = sourceIpAddress;
        this.classificationConfidence = classificationConfidence;
        this.outlierScore = outlierScore;
    }

    public String getSourceIpAddress() {
        return this.sourceIpAddress;
    }

    public void setSourceIpAddress(String sourceIpAddress) {
        this.sourceIpAddress = sourceIpAddress;
    }

    public String getClassificationConfidence() {
        return this.classificationConfidence;
    }

    public void setClassificationConfidence(String classificationConfidence) {
        this.classificationConfidence = classificationConfidence;
    }

    public String getOutlierScore() {
        return this.outlierScore;
    }

    public void setOutlierScore(String outlierScore) {
        this.outlierScore = outlierScore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SyslogThreatIncident)) {
            return false;
        }
        SyslogThreatIncident syslogThreatIncident = (SyslogThreatIncident) o;
        return super.equals((Incident) o)
                && Objects.equals(sourceIpAddress, syslogThreatIncident.sourceIpAddress)
                && Objects.equals(classificationConfidence, syslogThreatIncident.classificationConfidence)
                && Objects.equals(outlierScore, syslogThreatIncident.outlierScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sourceIpAddress, classificationConfidence, outlierScore);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() - 1) +
                ", sourceIpAddress='" + getSourceIpAddress() + "'" +
                ", classificationConfidence='" + getClassificationConfidence() + "'" +
                ", outlierScore='" + getOutlierScore() + "'" +
                "}";
    }

}
