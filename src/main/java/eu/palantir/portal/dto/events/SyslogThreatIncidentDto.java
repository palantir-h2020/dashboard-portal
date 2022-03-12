package eu.palantir.portal.dto.events;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyslogThreatIncidentDto extends IncidentDto {

    private String sourceIpAddress;

    private String classificationConfidence;

    private String outlierScore;

    public SyslogThreatIncidentDto() {
    }

    public SyslogThreatIncidentDto(Long id, String type, String location, String name, String description,
            Instant createdTimestamp, String sourceIpAddress, String classificationConfidence,
            String outlierScore) {
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
        if (!(o instanceof SyslogThreatIncidentDto)) {
            return false;
        }
        SyslogThreatIncidentDto syslogThreatIncidentDto = (SyslogThreatIncidentDto) o;
        return super.equals((IncidentDto) o)
                && Objects.equals(sourceIpAddress, syslogThreatIncidentDto.sourceIpAddress)
                && Objects.equals(classificationConfidence, syslogThreatIncidentDto.classificationConfidence)
                && Objects.equals(outlierScore, syslogThreatIncidentDto.outlierScore);
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
