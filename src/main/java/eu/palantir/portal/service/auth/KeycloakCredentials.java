package eu.palantir.portal.service.auth;

public class KeycloakCredentials {

    public KeycloakCredentials(String type, String value, boolean temporary) {
        this.type = type;
        this.value = value;
        this.temporary = temporary;
    }

    // always is set to password
    public String type;
    public boolean temporary;
    public String value;
}
