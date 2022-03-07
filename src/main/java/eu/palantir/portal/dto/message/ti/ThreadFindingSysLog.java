
package eu.palantir.portal.dto.message.ti;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "AnomalyDetectionSyslog",
        "Threat_Label",
        "Classification_Confidence",
        "Outlier_Score"
})
public class ThreadFindingSysLog implements Serializable {

    @JsonProperty("AnomalyDetectionSyslog")
    private String anomalyDetectionSyslog;
    @JsonProperty("Threat_Label")
    private String threatLabel;
    @JsonProperty("Classification_Confidence")
    private Double classificationConfidence;
    @JsonProperty("Outlier_Score")
    private Double outlierScore;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ThreadFindingSysLog() {
    }

    public ThreadFindingSysLog(String anomalyDetectionSyslog, String threatLabel, Double classificationConfidence,
            Double outlierScore) {
        super();
        this.anomalyDetectionSyslog = anomalyDetectionSyslog;
        this.threatLabel = threatLabel;
        this.classificationConfidence = classificationConfidence;
        this.outlierScore = outlierScore;
    }

    @JsonProperty("AnomalyDetectionSyslog")
    public String getAnomalyDetectionSyslog() {
        return anomalyDetectionSyslog;
    }

    @JsonProperty("Threat_Label")
    public String getThreatLabel() {
        return threatLabel;
    }

    @JsonProperty("Classification_Confidence")
    public Double getClassificationConfidence() {
        return classificationConfidence;
    }

    @JsonProperty("Outlier_Score")
    public Double getOutlierScore() {
        return outlierScore;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ThreadFindingSysLog.class.getName()).append('@')
                .append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("anomalyDetectionSyslog");
        sb.append('=');
        sb.append(((this.anomalyDetectionSyslog == null) ? "<null>" : this.anomalyDetectionSyslog));
        sb.append(',');
        sb.append("threatLabel");
        sb.append('=');
        sb.append(((this.threatLabel == null) ? "<null>" : this.threatLabel));
        sb.append(',');
        sb.append("classificationConfidence");
        sb.append('=');
        sb.append(((this.classificationConfidence == null) ? "<null>" : this.classificationConfidence));
        sb.append(',');
        sb.append("outlierScore");
        sb.append('=');
        sb.append(((this.outlierScore == null) ? "<null>" : this.outlierScore));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? "<null>" : this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.threatLabel == null) ? 0 : this.threatLabel.hashCode()));
        result = ((result * 31) + ((this.anomalyDetectionSyslog == null) ? 0 : this.anomalyDetectionSyslog.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31)
                + ((this.classificationConfidence == null) ? 0 : this.classificationConfidence.hashCode()));
        result = ((result * 31) + ((this.outlierScore == null) ? 0 : this.outlierScore.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ThreadFindingSysLog) == false) {
            return false;
        }
        ThreadFindingSysLog rhs = ((ThreadFindingSysLog) other);
        return ((((((this.threatLabel == rhs.threatLabel)
                || ((this.threatLabel != null) && this.threatLabel.equals(rhs.threatLabel)))
                && ((this.anomalyDetectionSyslog == rhs.anomalyDetectionSyslog)
                        || ((this.anomalyDetectionSyslog != null)
                                && this.anomalyDetectionSyslog.equals(rhs.anomalyDetectionSyslog))))
                && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null)
                        && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.classificationConfidence == rhs.classificationConfidence)
                        || ((this.classificationConfidence != null)
                                && this.classificationConfidence.equals(rhs.classificationConfidence))))
                && ((this.outlierScore == rhs.outlierScore)
                        || ((this.outlierScore != null) && this.outlierScore.equals(rhs.outlierScore))));
    }

}
