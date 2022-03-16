package eu.palantir.portal.dto.events;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetflowIncidentDto extends IncidentDto {

    private String timeStart;

    private String timeEnd;

    private Double duration;

    private String sourceIpAddress;

    private String destinationIpAddress;

    private Integer sourcePort;

    private Integer destinationPort;

    private String protocol;

    private Float classificationConfidence;

    private Float outlierScore;

    public NetflowIncidentDto() {
    }

    public NetflowIncidentDto(Long id, String type, String location, String name, String description,
            Instant createdTimestamp, String timeStart, String timeEnd, Double duration, String sourceIpAddress,
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

    public String getTimeStart() {
        return this.timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return this.timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
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
        if (!(o instanceof NetflowIncidentDto)) {
            return false;
        }
        NetflowIncidentDto netflowIncidentDto = (NetflowIncidentDto) o;
        return super.equals((IncidentDto) o)
                && Objects.equals(timeStart, netflowIncidentDto.timeStart)
                && Objects.equals(timeEnd, netflowIncidentDto.timeEnd)
                && Objects.equals(duration, netflowIncidentDto.duration)
                && Objects.equals(sourceIpAddress, netflowIncidentDto.sourceIpAddress)
                && Objects.equals(destinationIpAddress, netflowIncidentDto.destinationIpAddress)
                && Objects.equals(sourcePort, netflowIncidentDto.sourcePort)
                && Objects.equals(destinationPort, netflowIncidentDto.destinationPort)
                && Objects.equals(protocol, netflowIncidentDto.protocol)
                && Objects.equals(classificationConfidence, netflowIncidentDto.classificationConfidence)
                && Objects.equals(outlierScore, netflowIncidentDto.outlierScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timeStart, timeEnd, duration, sourceIpAddress, destinationIpAddress,
                sourcePort, destinationPort, protocol, classificationConfidence, outlierScore);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() - 1) +
                " timeStart='" + getTimeStart() + "'" +
                ", timeEnd='" + getTimeEnd() + "'" +
                ", duration='" + getDuration() + "'" +
                ", sourceIpAddress='" + getSourceIpAddress() + "'" +
                ", destinationIpAddress='" + getDestinationIpAddress() + "'" +
                ", sourcePort='" + getSourcePort() + "'" +
                ", destinationPort='" + getDestinationPort() + "'" +
                ", protocol='" + getProtocol() + "'" +
                ", classificationConfidence='" + getClassificationConfidence() + "'" +
                ", outlierScore='" + getOutlierScore() + "'" +
                "}";
    }

}
