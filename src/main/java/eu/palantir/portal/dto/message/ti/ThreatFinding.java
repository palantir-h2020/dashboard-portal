
package eu.palantir.portal.dto.message.ti;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Time_Start",
        "Time_End",
        "Time_Duration",
        "Source_Address",
        "Destination_Address",
        "Source_Port",
        "Destination_Port",
        "Protocol",
        "Flag",
        "Soure_tos",
        "Input_packets",
        "Input_bytes"
})
public class ThreatFinding implements Serializable {

    @JsonProperty("Time_Start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date timeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonProperty("Time_End")
    private Date timeEnd;
    @JsonProperty("Time_Duration")
    private Double timeDuration;
    @JsonProperty("Source_Address")
    private String sourceAddress;
    @JsonProperty("Destination_Address")
    private String destinationAddress;
    @JsonProperty("Source_Port")
    private Integer sourcePort;
    @JsonProperty("Destination_Port")
    private Integer destinationPort;
    @JsonProperty("Protocol")
    private String protocol;
    @JsonProperty("Flag")
    private String flag;
    @JsonProperty("Soure_tos")
    private Integer soureTos;
    @JsonProperty("Input_packets")
    private Integer inputPackets;
    @JsonProperty("Input_bytes")
    private Integer inputBytes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ThreatFinding() {
    }

    /**
     *
     * @param timeEnd
     * @param destinationPort
     * @param inputPackets
     * @param inputBytes
     * @param sourcePort
     * @param protocol
     * @param timeStart
     * @param sourceAddress
     * @param destinationAddress
     * @param flag
     * @param soureTos
     * @param timeDuration
     */
    public ThreatFinding(Date timeStart, Date timeEnd, Double timeDuration, String sourceAddress,
            String destinationAddress, Integer sourcePort, Integer destinationPort, String protocol, String flag,
            Integer soureTos, Integer inputPackets, Integer inputBytes) {
        super();
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.timeDuration = timeDuration;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.protocol = protocol;
        this.flag = flag;
        this.soureTos = soureTos;
        this.inputPackets = inputPackets;
        this.inputBytes = inputBytes;
    }

    @JsonProperty("Time_Start")
    public Date getTimeStart() {
        return timeStart;
    }

    @JsonProperty("Time_End")
    public Date getTimeEnd() {
        return timeEnd;
    }

    @JsonProperty("Time_Duration")
    public Double getTimeDuration() {
        return timeDuration;
    }

    @JsonProperty("Source_Address")
    public String getSourceAddress() {
        return sourceAddress;
    }

    @JsonProperty("Destination_Address")
    public String getDestinationAddress() {
        return destinationAddress;
    }

    @JsonProperty("Source_Port")
    public Integer getSourcePort() {
        return sourcePort;
    }

    @JsonProperty("Destination_Port")
    public Integer getDestinationPort() {
        return destinationPort;
    }

    @JsonProperty("Protocol")
    public String getProtocol() {
        return protocol;
    }

    @JsonProperty("Flag")
    public String getFlag() {
        return flag;
    }

    @JsonProperty("Soure_tos")
    public Integer getSoureTos() {
        return soureTos;
    }

    @JsonProperty("Input_packets")
    public Integer getInputPackets() {
        return inputPackets;
    }

    @JsonProperty("Input_bytes")
    public Integer getInputBytes() {
        return inputBytes;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        return "{" +
                " timeStart='" + getTimeStart() + "'" +
                ", timeEnd='" + getTimeEnd() + "'" +
                ", timeDuration='" + getTimeDuration() + "'" +
                ", sourceAddress='" + getSourceAddress() + "'" +
                ", destinationAddress='" + getDestinationAddress() + "'" +
                ", sourcePort='" + getSourcePort() + "'" +
                ", destinationPort='" + getDestinationPort() + "'" +
                ", protocol='" + getProtocol() + "'" +
                ", flag='" + getFlag() + "'" +
                ", soureTos='" + getSoureTos() + "'" +
                ", inputPackets='" + getInputPackets() + "'" +
                ", inputBytes='" + getInputBytes() + "'" +
                ", additionalProperties='" + getAdditionalProperties() + "'" +
                "}";
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.timeEnd == null) ? 0 : this.timeEnd.hashCode()));
        result = ((result * 31) + ((this.destinationPort == null) ? 0 : this.destinationPort.hashCode()));
        result = ((result * 31) + ((this.sourcePort == null) ? 0 : this.sourcePort.hashCode()));
        result = ((result * 31) + ((this.sourceAddress == null) ? 0 : this.sourceAddress.hashCode()));
        result = ((result * 31) + ((this.destinationAddress == null) ? 0 : this.destinationAddress.hashCode()));
        result = ((result * 31) + ((this.flag == null) ? 0 : this.flag.hashCode()));
        result = ((result * 31) + ((this.timeDuration == null) ? 0 : this.timeDuration.hashCode()));
        result = ((result * 31) + ((this.inputPackets == null) ? 0 : this.inputPackets.hashCode()));
        result = ((result * 31) + ((this.inputBytes == null) ? 0 : this.inputBytes.hashCode()));
        result = ((result * 31) + ((this.protocol == null) ? 0 : this.protocol.hashCode()));
        result = ((result * 31) + ((this.timeStart == null) ? 0 : this.timeStart.hashCode()));
        result = ((result * 31) + ((this.soureTos == null) ? 0 : this.soureTos.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ThreatFinding) == false) {
            return false;
        }
        ThreatFinding rhs = ((ThreatFinding) other);
        return ((((((((((((((this.timeEnd == rhs.timeEnd)
                || ((this.timeEnd != null) && this.timeEnd.equals(rhs.timeEnd)))
                && ((this.destinationPort == rhs.destinationPort)
                        || ((this.destinationPort != null) && this.destinationPort.equals(rhs.destinationPort))))
                && ((this.sourcePort == rhs.sourcePort)
                        || ((this.sourcePort != null) && this.sourcePort.equals(rhs.sourcePort))))
                && ((this.sourceAddress == rhs.sourceAddress)
                        || ((this.sourceAddress != null) && this.sourceAddress.equals(rhs.sourceAddress))))
                && ((this.destinationAddress == rhs.destinationAddress) || ((this.destinationAddress != null)
                        && this.destinationAddress.equals(rhs.destinationAddress))))
                && ((this.flag == rhs.flag) || ((this.flag != null) && this.flag.equals(rhs.flag))))
                && ((this.timeDuration == rhs.timeDuration)
                        || ((this.timeDuration != null) && this.timeDuration.equals(rhs.timeDuration))))
                && ((this.inputPackets == rhs.inputPackets)
                        || ((this.inputPackets != null) && this.inputPackets.equals(rhs.inputPackets))))
                && ((this.inputBytes == rhs.inputBytes)
                        || ((this.inputBytes != null) && this.inputBytes.equals(rhs.inputBytes))))
                && ((this.protocol == rhs.protocol) || ((this.protocol != null) && this.protocol.equals(rhs.protocol))))
                && ((this.timeStart == rhs.timeStart)
                        || ((this.timeStart != null) && this.timeStart.equals(rhs.timeStart))))
                && ((this.soureTos == rhs.soureTos) || ((this.soureTos != null) && this.soureTos.equals(rhs.soureTos))))
                && ((this.additionalProperties == rhs.additionalProperties) || ((this.additionalProperties != null)
                        && this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
