package eu.palantir.portal.dto.message.ti;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Threat_Finding",
        "Threat_Label",
        "Threat_Category",
        "Classification_Confidence",
        "Outlier_Score"
})
public class ThreatFindingNetFlow implements Serializable {

    @JsonProperty("Threat_Finding")
    private ThreatFinding threatFinding;
    @JsonProperty("Threat_Label")
    private String threatLabel;
    @JsonProperty("Threat_Category")
    private String threatCategory;
    @JsonProperty("Classification_Confidence")
    private Float classificationConfidence;
    @JsonProperty("Outlier_Score")
    private Float outlierScore;

    public ThreatFindingNetFlow() {
    }

    /**
     *
     * @param threatFinding
     * @param outlierScore
     * @param threatLabel
     * @param threatCategory
     * @param classificationConfidence
     */
    public ThreatFindingNetFlow(ThreatFinding threatFinding, String threatLabel, String threatCategory,
            Float classificationConfidence, Float outlierScore) {
        super();
        this.threatFinding = threatFinding;
        this.threatLabel = threatLabel;
        this.threatCategory = threatCategory;
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

    @JsonProperty("Threat_Category")
    public String getThreatCategory() {
        return threatCategory;
    }

    @JsonProperty("Threat_Category")
    public void setThreatCategory(String threatCategory) {
        this.threatCategory = threatCategory;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ThreatFindingNetFlow)) {
            return false;
        }
        ThreatFindingNetFlow threatFindingNetFlow = (ThreatFindingNetFlow) o;
        return Objects.equals(threatFinding, threatFindingNetFlow.threatFinding)
                && Objects.equals(threatLabel, threatFindingNetFlow.threatLabel)
                && Objects.equals(threatCategory, threatFindingNetFlow.threatCategory)
                && Objects.equals(classificationConfidence, threatFindingNetFlow.classificationConfidence)
                && Objects.equals(outlierScore, threatFindingNetFlow.outlierScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threatFinding, threatLabel, threatCategory, classificationConfidence, outlierScore);
    }

    @Override
    public String toString() {
        return "{" +
                " threatFinding='" + getThreatFinding() + "'" +
                ", threatLabel='" + getThreatLabel() + "'" +
                ", threatCategory='" + getThreatCategory() + "'" +
                ", classificationConfidence='" + getClassificationConfidence() + "'" +
                ", outlierScore='" + getOutlierScore() + "'" +
                "}";
    }

}
