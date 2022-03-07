
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
        "mechName",
        "nature",
        "scId",
        "billingPeriod",
        "name"
})
public class SMRequestParameter implements Serializable {

    @JsonProperty("mechName")
    private String mechName;
    @JsonProperty("nature")
    private String nature;
    @JsonProperty("scId")
    private String scId;
    @JsonProperty("billingPeriod")
    private String billingPeriod;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public SMRequestParameter() {
    }

    /**
     *
     * @param nature
     * @param billingPeriod
     * @param name
     * @param mechName
     * @param scId
     */
    public SMRequestParameter(String mechName, String nature, String scId, String billingPeriod, String name) {
        super();
        this.mechName = mechName;
        this.nature = nature;
        this.scId = scId;
        this.billingPeriod = billingPeriod;
        this.name = name;
    }

    @JsonProperty("mechName")
    public String getMechName() {
        return mechName;
    }

    @JsonProperty("nature")
    public String getNature() {
        return nature;
    }

    @JsonProperty("scId")
    public String getScId() {
        return scId;
    }

    @JsonProperty("billingPeriod")
    public String getBillingPeriod() {
        return billingPeriod;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SMRequestParameter.class.getName()).append('@')
                .append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("mechName");
        sb.append('=');
        sb.append(((this.mechName == null) ? "<null>" : this.mechName));
        sb.append(',');
        sb.append("nature");
        sb.append('=');
        sb.append(((this.nature == null) ? "<null>" : this.nature));
        sb.append(',');
        sb.append("scId");
        sb.append('=');
        sb.append(((this.scId == null) ? "<null>" : this.scId));
        sb.append(',');
        sb.append("billingPeriod");
        sb.append('=');
        sb.append(((this.billingPeriod == null) ? "<null>" : this.billingPeriod));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
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
        result = ((result * 31) + ((this.nature == null) ? 0 : this.nature.hashCode()));
        result = ((result * 31) + ((this.billingPeriod == null) ? 0 : this.billingPeriod.hashCode()));
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.mechName == null) ? 0 : this.mechName.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.scId == null) ? 0 : this.scId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SMRequestParameter) == false) {
            return false;
        }
        SMRequestParameter rhs = ((SMRequestParameter) other);
        return (((((((this.nature == rhs.nature) || ((this.nature != null) && this.nature.equals(rhs.nature)))
                && ((this.billingPeriod == rhs.billingPeriod)
                        || ((this.billingPeriod != null) && this.billingPeriod.equals(rhs.billingPeriod))))
                && ((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name))))
                && ((this.mechName == rhs.mechName) || ((this.mechName != null) && this.mechName.equals(rhs.mechName))))
                && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null)
                        && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.scId == rhs.scId) || ((this.scId != null) && this.scId.equals(rhs.scId))));
    }

}
