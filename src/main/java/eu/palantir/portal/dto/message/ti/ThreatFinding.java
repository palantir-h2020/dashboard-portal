package eu.palantir.portal.dto.message.ti;

import java.io.Serializable;
import java.util.Objects;

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
    private String timeStart;
    @JsonProperty("Time_End")
    private String timeEnd;
    @JsonProperty("Time_Duration")
    private String timeDuration;
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
    public ThreatFinding(String timeStart, String timeEnd, String timeDuration, String sourceAddress,
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
    public String getTimeStart() {
        return timeStart;
    }

    @JsonProperty("Time_Start")
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    @JsonProperty("Time_End")
    public String getTimeEnd() {
        return timeEnd;
    }

    @JsonProperty("Time_End")
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    @JsonProperty("Time_Duration")
    public String getTimeDuration() {
        return timeDuration;
    }

    @JsonProperty("Time_Duration")
    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    @JsonProperty("Source_Address")
    public String getSourceAddress() {
        return sourceAddress;
    }

    @JsonProperty("Source_Address")
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    @JsonProperty("Destination_Address")
    public String getDestinationAddress() {
        return destinationAddress;
    }

    @JsonProperty("Destination_Address")
    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    @JsonProperty("Source_Port")
    public Integer getSourcePort() {
        return sourcePort;
    }

    @JsonProperty("Source_Port")
    public void setSourcePort(Integer sourcePort) {
        this.sourcePort = sourcePort;
    }

    @JsonProperty("Destination_Port")
    public Integer getDestinationPort() {
        return destinationPort;
    }

    @JsonProperty("Destination_Port")
    public void setDestinationPort(Integer destinationPort) {
        this.destinationPort = destinationPort;
    }

    @JsonProperty("Protocol")
    public String getProtocol() {
        return protocol;
    }

    @JsonProperty("Protocol")
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @JsonProperty("Flag")
    public String getFlag() {
        return flag;
    }

    @JsonProperty("Flag")
    public void setFlag(String flag) {
        this.flag = flag;
    }

    @JsonProperty("Soure_tos")
    public Integer getSoureTos() {
        return soureTos;
    }

    @JsonProperty("Soure_tos")
    public void setSoureTos(Integer soureTos) {
        this.soureTos = soureTos;
    }

    @JsonProperty("Input_packets")
    public Integer getInputPackets() {
        return inputPackets;
    }

    @JsonProperty("Input_packets")
    public void setInputPackets(Integer inputPackets) {
        this.inputPackets = inputPackets;
    }

    @JsonProperty("Input_bytes")
    public Integer getInputBytes() {
        return inputBytes;
    }

    @JsonProperty("Input_bytes")
    public void setInputBytes(Integer inputBytes) {
        this.inputBytes = inputBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ThreatFinding)) {
            return false;
        }
        ThreatFinding threatFinding = (ThreatFinding) o;
        return Objects.equals(timeStart, threatFinding.timeStart) && Objects.equals(timeEnd, threatFinding.timeEnd)
                && Objects.equals(timeDuration, threatFinding.timeDuration)
                && Objects.equals(sourceAddress, threatFinding.sourceAddress)
                && Objects.equals(destinationAddress, threatFinding.destinationAddress)
                && Objects.equals(sourcePort, threatFinding.sourcePort)
                && Objects.equals(destinationPort, threatFinding.destinationPort)
                && Objects.equals(protocol, threatFinding.protocol) && Objects.equals(flag, threatFinding.flag)
                && Objects.equals(soureTos, threatFinding.soureTos)
                && Objects.equals(inputPackets, threatFinding.inputPackets)
                && Objects.equals(inputBytes, threatFinding.inputBytes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStart, timeEnd, timeDuration, sourceAddress, destinationAddress, sourcePort,
                destinationPort, protocol, flag, soureTos, inputPackets, inputBytes);
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
                "}";
    }

}
