
package eu.palantir.portal.dto.message.ti;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Threat_Finding",
        "Threat_Label",
        "Classification_Confidence",
        "Outlier_Score"
})
public class ThreatFindingNetFlow implements Serializable {

    @JsonProperty("Threat_Finding")
    private ThreatFinding threatFinding;
    @JsonProperty("Threat_Label")
    private String threatLabel;
    @JsonProperty("Classification_Confidence")
    private Float classificationConfidence;
    @JsonProperty("Outlier_Score")
    private Float outlierScore;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ThreatFindingNetFlow() {
    }

    /**
     *
     * @param threatFinding
     * @param outlierScore
     * @param threatLabel
     * @param classificationConfidence
     */
    public ThreatFindingNetFlow(ThreatFinding threatFinding, String threatLabel,
            Float classificationConfidence,
            Float outlierScore) {
        super();
        this.threatFinding = threatFinding;
        this.threatLabel = threatLabel;
        this.classificationConfidence = classificationConfidence;
        this.outlierScore = outlierScore;
    }

    @JsonProperty("Threat_Finding")
    public ThreatFinding getThreatFinding() {
        return threatFinding;
    }

    @JsonProperty("Threat_Finding")
    public void setThreatFinding(ThreatFinding threatFinding) {
        this.threatFinding = threatFinding;
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
    public Float getOutlierScore() {
        return outlierScore;
    }

    @JsonProperty("Outlier_Score")
    public void setOutlierScore(Float outlierScore) {
        this.outlierScore = outlierScore;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "{" +
                " threatFinding='" + getThreatFinding() + "'" +
                ", threatLabel='" + getThreatLabel() + "'" +
                ", classificationConfidence='" + getClassificationConfidence() + "'" +
                ", outlierScore='" + getOutlierScore() + "'" +
                ", additionalProperties='" + getAdditionalProperties() + "'" +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.threatFinding == null) ? 0 : this.threatFinding.hashCode()));
        result = ((result * 31) + ((this.threatLabel == null) ? 0 : this.threatLabel.hashCode()));
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
        if ((other instanceof ThreatFindingNetFlow) == false) {
            return false;
        }
        ThreatFindingNetFlow rhs = ((ThreatFindingNetFlow) other);
        return ((((((this.threatFinding == rhs.threatFinding)
                || ((this.threatFinding != null) && this.threatFinding.equals(rhs.threatFinding)))
                && ((this.threatLabel == rhs.threatLabel)
                        || ((this.threatLabel != null) && this.threatLabel.equals(rhs.threatLabel))))
                && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null)
                        && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.classificationConfidence == rhs.classificationConfidence)
                        || ((this.classificationConfidence != null)
                                && this.classificationConfidence.equals(rhs.classificationConfidence))))
                && ((this.outlierScore == rhs.outlierScore)
                        || ((this.outlierScore != null) && this.outlierScore.equals(rhs.outlierScore))));
    }

}
