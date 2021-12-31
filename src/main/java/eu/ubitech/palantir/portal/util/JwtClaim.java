package eu.ubitech.palantir.portal.util;

public enum JwtClaim {

  USER_ID {
    // overriding toString() for SMALL
    public String toString() {
      return "userid";
    }
  },

}
