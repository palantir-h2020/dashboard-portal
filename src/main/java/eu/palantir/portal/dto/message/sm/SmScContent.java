
package eu.palantir.portal.dto.message.sm;

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
        "scId",
        "billingModel",
        "sla",
        "infrastructureId",
        "deploymentModel",
        "typeCounterMeasure"
})
public class SmScContent implements Serializable {

    @JsonProperty("scId")
    private String scId;
    @JsonProperty("billingModel")
    private String billingModel;
    @JsonProperty("sla")
    private Double sla;
    @JsonProperty("infrastructureId")
    private String infrastructureId;
    @JsonProperty("deploymentModel")
    private String deploymentModel;
    @JsonProperty("typeCounterMeasure")
    private String typeCounterMeasure;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public SmScContent() {
    }

    /**
     *
     * @param deploymentModel
     * @param infrastructureId
     * @param typeCounterMeasure
     * @param billingModel
     * @param sla
     * @param scId
     */
    public SmScContent(String scId, String billingModel, Double sla, String infrastructureId, String deploymentModel,
            String typeCounterMeasure) {
        super();
        this.scId = scId;
        this.billingModel = billingModel;
        this.sla = sla;
        this.infrastructureId = infrastructureId;
        this.deploymentModel = deploymentModel;
        this.typeCounterMeasure = typeCounterMeasure;
    }

    @JsonProperty("scId")
    public String getScId() {
        return scId;
    }

    @JsonProperty("billingModel")
    public String getBillingModel() {
        return billingModel;
    }

    @JsonProperty("sla")
    public Double getSla() {
        return sla;
    }

    @JsonProperty("infrastructureId")
    public String getInfrastructureId() {
        return infrastructureId;
    }

    @JsonProperty("deploymentModel")
    public String getDeploymentModel() {
        return deploymentModel;
    }

    @JsonProperty("typeCounterMeasure")
    public String getTypeCounterMeasure() {
        return typeCounterMeasure;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        return "{" +
                " scId='" + getScId() + "'" +
                ", billingModel='" + getBillingModel() + "'" +
                ", sla='" + getSla() + "'" +
                ", infrastructureId='" + getInfrastructureId() + "'" +
                ", deploymentModel='" + getDeploymentModel() + "'" +
                ", typeCounterMeasure='" + getTypeCounterMeasure() + "'" +
                ", additionalProperties='" + getAdditionalProperties() + "'" +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.deploymentModel == null) ? 0 : this.deploymentModel.hashCode()));
        result = ((result * 31) + ((this.infrastructureId == null) ? 0 : this.infrastructureId.hashCode()));
        result = ((result * 31) + ((this.typeCounterMeasure == null) ? 0 : this.typeCounterMeasure.hashCode()));
        result = ((result * 31) + ((this.billingModel == null) ? 0 : this.billingModel.hashCode()));
        result = ((result * 31) + ((this.sla == null) ? 0 : this.sla.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.scId == null) ? 0 : this.scId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SmScContent) == false) {
            return false;
        }
        SmScContent rhs = ((SmScContent) other);
        return ((((((((this.deploymentModel == rhs.deploymentModel)
                || ((this.deploymentModel != null) && this.deploymentModel.equals(rhs.deploymentModel)))
                && ((this.infrastructureId == rhs.infrastructureId)
                        || ((this.infrastructureId != null) && this.infrastructureId.equals(rhs.infrastructureId))))
                && ((this.typeCounterMeasure == rhs.typeCounterMeasure) || ((this.typeCounterMeasure != null)
                        && this.typeCounterMeasure.equals(rhs.typeCounterMeasure))))
                && ((this.billingModel == rhs.billingModel)
                        || ((this.billingModel != null) && this.billingModel.equals(rhs.billingModel))))
                && ((this.sla == rhs.sla) || ((this.sla != null) && this.sla.equals(rhs.sla))))
                && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null)
                        && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.scId == rhs.scId) || ((this.scId != null) && this.scId.equals(rhs.scId))));
    }

}
