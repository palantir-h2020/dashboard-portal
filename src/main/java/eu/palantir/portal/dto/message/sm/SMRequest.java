
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
        "session",
        "realm",
        "action",
        "parameter"
})
public class SMRequest implements Serializable {

    @JsonProperty("session")
    private String session;
    @JsonProperty("realm")
    private String realm;
    @JsonProperty("action")
    private String action;
    @JsonProperty("parameter")
    private List<SMRequestParameter> parameter = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public SMRequest() {
    }

    /**
     *
     * @param session
     * @param parameter
     * @param action
     * @param realm
     */
    public SMRequest(String session, String realm, String action, List<SMRequestParameter> parameter) {
        super();
        this.session = session;
        this.realm = realm;
        this.action = action;
        this.parameter = parameter;
    }

    @JsonProperty("session")
    public String getSession() {
        return session;
    }

    @JsonProperty("realm")
    public String getRealm() {
        return realm;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("parameter")
    public List<SMRequestParameter> getParameter() {
        return parameter;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        return "{" +
                " session='" + getSession() + "'" +
                ", realm='" + getRealm() + "'" +
                ", action='" + getAction() + "'" +
                ", parameter='" + getParameter() + "'" +
                ", additionalProperties='" + getAdditionalProperties() + "'" +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.action == null) ? 0 : this.action.hashCode()));
        result = ((result * 31) + ((this.realm == null) ? 0 : this.realm.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.session == null) ? 0 : this.session.hashCode()));
        result = ((result * 31) + ((this.parameter == null) ? 0 : this.parameter.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SMRequest) == false) {
            return false;
        }
        SMRequest rhs = ((SMRequest) other);
        return ((((((this.action == rhs.action) || ((this.action != null) && this.action.equals(rhs.action)))
                && ((this.realm == rhs.realm) || ((this.realm != null) && this.realm.equals(rhs.realm))))
                && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null)
                        && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.session == rhs.session) || ((this.session != null) && this.session.equals(rhs.session))))
                && ((this.parameter == rhs.parameter)
                        || ((this.parameter != null) && this.parameter.equals(rhs.parameter))));
    }

}
