
package eu.palantir.portal.dto.message.sm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cost",
        "content",
        "effectivelyDeployed"
})
public class Content implements Serializable {

    @JsonProperty("cost")
    private Double cost;
    @JsonProperty("content")
    private List<SmScContent> content = null;
    @JsonProperty("effectivelyDeployed")
    private Boolean effectivelyDeployed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7654476732310284946L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Content() {
    }

    /**
     * 
     * @param cost
     * @param content
     * @param effectivelyDeployed
     */
    public Content(Double cost, List<SmScContent> content, Boolean effectivelyDeployed) {
        super();
        this.cost = cost;
        this.content = content;
        this.effectivelyDeployed = effectivelyDeployed;
    }

    @JsonProperty("cost")
    public Double getCost() {
        return cost;
    }

    @JsonProperty("content")
    public List<SmScContent> getContent() {
        return content;
    }

    @JsonProperty("effectivelyDeployed")
    public Boolean getEffectivelyDeployed() {
        return effectivelyDeployed;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Content.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
                .append('[');
        sb.append("cost");
        sb.append('=');
        sb.append(((this.cost == null) ? "<null>" : this.cost));
        sb.append(',');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null) ? "<null>" : this.content));
        sb.append(',');
        sb.append("effectivelyDeployed");
        sb.append('=');
        sb.append(((this.effectivelyDeployed == null) ? "<null>" : this.effectivelyDeployed));
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
        result = ((result * 31) + ((this.cost == null) ? 0 : this.cost.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.content == null) ? 0 : this.content.hashCode()));
        result = ((result * 31) + ((this.effectivelyDeployed == null) ? 0 : this.effectivelyDeployed.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Content) == false) {
            return false;
        }
        Content rhs = ((Content) other);
        return (((((this.cost == rhs.cost) || ((this.cost != null) && this.cost.equals(rhs.cost)))
                && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null)
                        && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.content == rhs.content) || ((this.content != null) && this.content.equals(rhs.content))))
                && ((this.effectivelyDeployed == rhs.effectivelyDeployed) || ((this.effectivelyDeployed != null)
                        && this.effectivelyDeployed.equals(rhs.effectivelyDeployed))));
    }

}
