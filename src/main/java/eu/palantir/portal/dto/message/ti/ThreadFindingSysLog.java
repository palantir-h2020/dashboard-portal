package eu.palantir.portal.dto.message.ti;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "AnomalyDetectionSyslog",
        "Threat_Label",
        "Classification_Confidence",
        "Outlier_Score",
        "Source_IP"
})
public class ThreadFindingSysLog implements Serializable {

    @JsonProperty("AnomalyDetectionSyslog")
    private String anomalyDetectionSyslog;
    @JsonProperty("Threat_Label")
    private String threatLabel;
    @JsonProperty("Classification_Confidence")
    private Float classificationConfidence;
    @JsonProperty("Outlier_Score")
    private String outlierScore;
    @JsonProperty("Source_IP")
    private String sourceIP;

    public ThreadFindingSysLog() {
    }

    /**
     *
     * @param anomalyDetectionSyslog
     * @param sourceIP
     * @param outlierScore
     * @param threatLabel
     * @param classificationConfidence
     */
    public ThreadFindingSysLog(String anomalyDetectionSyslog, String threatLabel, Float classificationConfidence,
            String outlierScore, String sourceIP) {
        super();
        this.anomalyDetectionSyslog = anomalyDetectionSyslog;
        this.threatLabel = threatLabel;
        this.classificationConfidence = classificationConfidence;
        this.outlierScore = outlierScore;
        this.sourceIP = sourceIP;
    }

    @JsonProperty("AnomalyDetectionSyslog")
    public String getAnomalyDetectionSyslog() {
        return anomalyDetectionSyslog;
    }

    @JsonProperty("AnomalyDetectionSyslog")
    public void setAnomalyDetectionSyslog(String anomalyDetectionSyslog) {
        this.anomalyDetectionSyslog = anomalyDetectionSyslog;
    }

    @JsonProperty("Threat_Label")
    public String getThreatLabel() {
        return threatLabel;
    }

    @JsonProperty("Threat_Label")
    public void setThreatLabel(String threatLabel) {
        this.threatLabel = threatLabel;
    }

    @JsonProperty("Classification_Confidence")
    public Float getClassificationConfidence() {
        return classificationConfidence;
    }

    @JsonProperty("Classification_Confidence")
    public void setClassificationConfidence(Float classificationConfidence) {
        this.classificationConfidence = classificationConfidence;
    }

    @JsonProperty("Outlier_Score")
    public String getOutlierScore() {
        return outlierScore;
    }

    @JsonProperty("Outlier_Score")
    public void setOutlierScore(String outlierScore) {
        this.outlierScore = outlierScore;
    }

    @JsonProperty("Source_IP")
    public String getSourceIP() {
        return sourceIP;
    }

    @JsonProperty("Source_IP")
    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ThreadFindingSysLog)) {
            return false;
        }
        ThreadFindingSysLog threadFindingSysLog = (ThreadFindingSysLog) o;
        return Objects.equals(anomalyDetectionSyslog, threadFindingSysLog.anomalyDetectionSyslog)
                && Objects.equals(threatLabel, threadFindingSysLog.threatLabel)
                && Objects.equals(classificationConfidence, threadFindingSysLog.classificationConfidence)
                && Objects.equals(outlierScore, threadFindingSysLog.outlierScore)
                && Objects.equals(sourceIP, threadFindingSysLog.sourceIP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anomalyDetectionSyslog, threatLabel, classificationConfidence, outlierScore, sourceIP);
    }

    @Override
    public String toString() {
        return "{" +
                " anomalyDetectionSyslog='" + getAnomalyDetectionSyslog() + "'" +
                ", threatLabel='" + getThreatLabel() + "'" +
                ", classificationConfidence='" + getClassificationConfidence() + "'" +
                ", outlierScore='" + getOutlierScore() + "'" +
                ", sourceIP='" + getSourceIP() + "'" +
                "}";
    }

}
