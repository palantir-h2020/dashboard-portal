package eu.palantir.portal.util;

public enum JwtClaim {

    USER_ID("userid"),
    USER_FULL_NAME("userfullname");

    private final String description;

    private JwtClaim(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
