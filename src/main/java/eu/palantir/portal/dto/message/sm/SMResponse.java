
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
        "session",
        "success",
        "error",
        "content"
})
public class SMResponse implements Serializable {

    @JsonProperty("session")
    private Integer session;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("error")
    private Integer error;
    @JsonProperty("content")
    private Content content;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1815121366803791971L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SMResponse() {
    }

    /**
     * 
     * @param session
     * @param success
     * @param error
     * @param content
     */
    public SMResponse(Integer session, Boolean success, Integer error, Content content) {
        super();
        this.session = session;
        this.success = success;
        this.error = error;
        this.content = content;
    }

    @JsonProperty("session")
    public Integer getSession() {
        return session;
    }

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("error")
    public Integer getError() {
        return error;
    }

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SMResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
                .append('[');
        sb.append("session");
        sb.append('=');
        sb.append(((this.session == null) ? "<null>" : this.session));
        sb.append(',');
        sb.append("success");
        sb.append('=');
        sb.append(((this.success == null) ? "<null>" : this.success));
        sb.append(',');
        sb.append("error");
        sb.append('=');
        sb.append(((this.error == null) ? "<null>" : this.error));
        sb.append(',');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null) ? "<null>" : this.content));
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
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.error == null) ? 0 : this.error.hashCode()));
        result = ((result * 31) + ((this.session == null) ? 0 : this.session.hashCode()));
        result = ((result * 31) + ((this.success == null) ? 0 : this.success.hashCode()));
        result = ((result * 31) + ((this.content == null) ? 0 : this.content.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SMResponse) == false) {
            return false;
        }
        SMResponse rhs = ((SMResponse) other);
        return ((((((this.additionalProperties == rhs.additionalProperties)
                || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties)))
                && ((this.error == rhs.error) || ((this.error != null) && this.error.equals(rhs.error))))
                && ((this.session == rhs.session) || ((this.session != null) && this.session.equals(rhs.session))))
                && ((this.success == rhs.success) || ((this.success != null) && this.success.equals(rhs.success))))
                && ((this.content == rhs.content) || ((this.content != null) && this.content.equals(rhs.content))));
    }

}
