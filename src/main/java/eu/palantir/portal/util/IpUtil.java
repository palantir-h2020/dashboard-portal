package eu.palantir.portal.util;

import java.util.logging.Logger;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSection;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressString;

public final class IpUtil {

    private static final Logger LOG = Logger.getLogger(Util.class.getName());

    /**
     * Check if a given IP address is in the given sequential IP range.
     * Supports both IPv4 and IPv6 addresses.
     *
     * In case of error, returns false.
     *
     * @param inputIP
     * @param rangeStartIP
     * @param rangeEndIP
     * @return
     */
    public static boolean checkIPIsInSeqRange(String inputIP, String rangeStartIP, String rangeEndIP) {
        try {
            IPAddress startIPAddress = new IPAddressString(rangeStartIP).getAddress();
            IPAddress endIPAddress = new IPAddressString(rangeEndIP).getAddress();
            IPAddressSeqRange ipRange = startIPAddress.toSequentialRange(endIPAddress);
            IPAddress inputIPAddress = new IPAddressString(inputIP).toAddress();

            return ipRange.contains(inputIPAddress);
        } catch (AddressStringException ex) {
            LOG.severe("IP address range check failed:" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Check if IP address is in subnet.
     * Subnet string supplied as CIDR, e.g., 10.10.20.0/30.
     *
     * @param inputIP
     * @param subnetString
     * @return
     */
    public static boolean checkIPIsInSubnet(String inputIP, String subnetString) {
        IPAddressString subnetIp = new IPAddressString(subnetString);
        IPAddressString inputIPAddress = new IPAddressString(inputIP);

        return subnetIp.contains(inputIPAddress);
    }

    /**
     * Check if IP address is in subnet.
     * Subnet string only contains the IP.
     * The prefix length is supplied separately as an integer.
     *
     * @param inputIP
     * @param subnetString
     * @param prefixLength
     * @return
     */
    public static boolean checkIPIsInSubnet(String inputIP, String subnetString, int prefixLength) {
        return checkIPIsInSubnet(inputIP, subnetString + "/" + prefixLength);
    }

}
