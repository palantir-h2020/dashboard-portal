package eu.palantir.portal.model;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;

@Entity
public class NetflowIncident extends Incident {

    private Date timeStart;

    private Date timeEnd;

    private Double duration;

    private String sourceIpAddress;

    private String destinationIpAddress;

    private Integer sourcePort;

    private Integer destinationPort;

    private String protocol;

    private Float classificationConfidence;

    private Float outlierScore;

    public NetflowIncident() {
    }

    public NetflowIncident(Long id, String type, String location, String name, String description,
            Instant createdTimestamp, Date timeStart, Date timeEnd, Double duration, String sourceIpAddress,
            String destinationIpAddress, Integer sourcePort, Integer destinationPort, String protocol,
            Float classificationConfidence, Float outlierScore) {
        this.setId(id);
        this.setType(type);
        this.setLocation(location);
        this.setName(name);
        this.setDescription(description);
        this.setCreatedTimestamp(createdTimestamp);
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.duration = duration;
        this.sourceIpAddress = sourceIpAddress;
        this.destinationIpAddress = destinationIpAddress;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.protocol = protocol;
        this.classificationConfidence = classificationConfidence;
        this.outlierScore = outlierScore;
    }

    public Date getTimeStart() {
        return this.timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return this.timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Double getDuration() {
        return this.duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getSourceIpAddress() {
        return this.sourceIpAddress;
    }

    public void setSourceIpAddress(String sourceIpAddress) {
        this.sourceIpAddress = sourceIpAddress;
    }

    public String getDestinationIpAddress() {
        return this.destinationIpAddress;
    }

    public void setDestinationIpAddress(String destinationIpAddress) {
        this.destinationIpAddress = destinationIpAddress;
    }

    public Integer getSourcePort() {
        return this.sourcePort;
    }

    public void setSourcePort(Integer sourcePort) {
        this.sourcePort = sourcePort;
    }

    public Integer getDestinationPort() {
        return this.destinationPort;
    }

    public void setDestinationPort(Integer destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Float getClassificationConfidence() {
        return this.classificationConfidence;
    }

    public void setClassificationConfidence(Float classificationConfidence) {
        this.classificationConfidence = classificationConfidence;
    }

    public Float getOutlierScore() {
        return this.outlierScore;
    }

    public void setOutlierScore(Float outlierScore) {
        this.outlierScore = outlierScore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NetflowIncident)) {
            return false;
        }
        NetflowIncident netflowThreatIncident = (NetflowIncident) o;
        return super.equals((Incident) o)
                && Objects.equals(timeStart, netflowThreatIncident.timeStart)
                && Objects.equals(timeEnd, netflowThreatIncident.timeEnd)
                && Objects.equals(duration, netflowThreatIncident.duration)
                && Objects.equals(destinationIpAddress, netflowThreatIncident.destinationIpAddress)
                && Objects.equals(sourcePort, netflowThreatIncident.sourcePort)
                && Objects.equals(destinationPort, netflowThreatIncident.destinationPort)
                && Objects.equals(protocol, netflowThreatIncident.protocol)
                && Objects.equals(classificationConfidence, netflowThreatIncident.classificationConfidence)
                && Objects.equals(outlierScore, netflowThreatIncident.outlierScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timeStart, timeEnd, duration, destinationIpAddress, sourcePort,
                destinationPort, protocol,
                classificationConfidence, outlierScore);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() - 1) +
                " timeStart='" + getTimeStart() + "'" +
                ", timeEnd='" + getTimeEnd() + "'" +
                ", duration='" + getDuration() + "'" +
                ", destinationIpAddress='" + getDestinationIpAddress() + "'" +
                ", sourcePort='" + getSourcePort() + "'" +
                ", destinationPort='" + getDestinationPort() + "'" +
                ", protocol='" + getProtocol() + "'" +
                ", classificationConfidence='" + getClassificationConfidence() + "'" +
                ", outlierScore='" + getOutlierScore() + "'" +
                "}";
    }

}
