package eu.palantir.portal.model;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;

@Entity
public class SyslogThreatIncident extends Incident {

    private String syslogMsg;

    private String classificationConfidence;

    private String outlierScore;

    public SyslogThreatIncident() {
    }

    public SyslogThreatIncident(Long id, String type, String sourceIpAddress, String name, String description,
            Instant createdTimestamp, String syslogMsg, String classificationConfidence, String outlierScore) {
        this.setId(id);
        this.setType(type);
        this.setSourceIpAddress(sourceIpAddress);
        this.setName(name);
        this.setDescription(description);
        this.setCreatedTimestamp(createdTimestamp);
        this.syslogMsg = syslogMsg;
        this.classificationConfidence = classificationConfidence;
        this.outlierScore = outlierScore;
    }

    public SyslogThreatIncident(String syslogMsg, String classificationConfidence, String outlierScore) {
        this.syslogMsg = syslogMsg;
        this.classificationConfidence = classificationConfidence;
        this.outlierScore = outlierScore;
    }

    public String getSyslogMsg() {
        return this.syslogMsg;
    }

    public void setSyslogMsg(String syslogMsg) {
        this.syslogMsg = syslogMsg;
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

    public SyslogThreatIncident syslogMsg(String syslogMsg) {
        setSyslogMsg(syslogMsg);
        return this;
    }

    public SyslogThreatIncident classificationConfidence(String classificationConfidence) {
        setClassificationConfidence(classificationConfidence);
        return this;
    }

    public SyslogThreatIncident outlierScore(String outlierScore) {
        setOutlierScore(outlierScore);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SyslogThreatIncident)) {
            return false;
        }
        SyslogThreatIncident syslogThreatIncident = (SyslogThreatIncident) o;
        return super.equals(o)
                && Objects.equals(syslogMsg, syslogThreatIncident.syslogMsg)
                && Objects.equals(classificationConfidence, syslogThreatIncident.classificationConfidence)
                && Objects.equals(outlierScore, syslogThreatIncident.outlierScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), syslogMsg, classificationConfidence, outlierScore);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() - 1) +
                " syslogMsg='" + getSyslogMsg() + "'" +
                ", classificationConfidence='" + getClassificationConfidence() + "'" +
                ", outlierScore='" + getOutlierScore() + "'" +
                "}";
    }

}
