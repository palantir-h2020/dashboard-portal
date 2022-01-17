package eu.palantir.portal.util;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.logging.Logger;

public final class Util {

    private static final Logger LOG = Logger.getLogger(Util.class.getName());

    /**
     * Checks if a string is UUID.
     *
     * @param string The string to be checked
     * @return True if it is a UUId
     */
    public static boolean isUuid(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static UUID randomUUID() {
        UUID uuid = UUID.randomUUID();
        // LOG.info("Initial UUID:" + uuid.toString());
        try {
            MessageDigest salt = MessageDigest.getInstance("SHA-256");
            salt.update(uuid.toString().getBytes("UTF-8"));
            // LOG.info("length of salt:"+salt.digest().length);
            // String encodedHexString = Hex.encodeHexString(salt.digest());
            // LOG.severe("encodedHexString:"+encodedHexString);
            uuid = UUID.nameUUIDFromBytes(salt.digest());
        } catch (Exception ex) {
            LOG.severe("UUID could not be generated:" + ex.getMessage());
            ex.printStackTrace();
        }
        // LOG.info("Final UUID:" + uuid.toString());
        return uuid;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
