
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
        "NetFlow_data",
        "Anomaly_Detection_Method",
        "Anomaly_Score"
})
public class TIAnomaly implements Serializable {

    @JsonProperty("NetFlow_data")
    private NetFlowData netFlowData;
    @JsonProperty("Anomaly_Detection_Method")
    private String anomalyDetectionMethod;
    @JsonProperty("Anomaly_Score")
    private Double anomalyScore;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public TIAnomaly() {
    }

    public TIAnomaly(NetFlowData netFlowData, String anomalyDetectionMethod, Double anomalyScore) {
        super();
        this.netFlowData = netFlowData;
        this.anomalyDetectionMethod = anomalyDetectionMethod;
        this.anomalyScore = anomalyScore;
    }

    @JsonProperty("NetFlow_data")
    public NetFlowData getNetFlowData() {
        return netFlowData;
    }

    @JsonProperty("Anomaly_Detection_Method")
    public String getAnomalyDetectionMethod() {
        return anomalyDetectionMethod;
    }

    @JsonProperty("Anomaly_Score")
    public Double getAnomalyScore() {
        return anomalyScore;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        return "{" +
                " netFlowData='" + getNetFlowData() + "'" +
                ", anomalyDetectionMethod='" + getAnomalyDetectionMethod() + "'" +
                ", anomalyScore='" + getAnomalyScore() + "'" +
                ", additionalProperties='" + getAdditionalProperties() + "'" +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.anomalyDetectionMethod == null) ? 0 : this.anomalyDetectionMethod.hashCode()));
        result = ((result * 31) + ((this.anomalyScore == null) ? 0 : this.anomalyScore.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.netFlowData == null) ? 0 : this.netFlowData.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TIAnomaly) == false) {
            return false;
        }
        TIAnomaly rhs = ((TIAnomaly) other);
        return (((((this.anomalyDetectionMethod == rhs.anomalyDetectionMethod) || ((this.anomalyDetectionMethod != null)
                && this.anomalyDetectionMethod.equals(rhs.anomalyDetectionMethod)))
                && ((this.anomalyScore == rhs.anomalyScore)
                        || ((this.anomalyScore != null) && this.anomalyScore.equals(rhs.anomalyScore))))
                && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null)
                        && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.netFlowData == rhs.netFlowData)
                        || ((this.netFlowData != null) && this.netFlowData.equals(rhs.netFlowData))));
    }

}
