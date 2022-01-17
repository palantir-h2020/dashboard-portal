package eu.palantir.portal.service.auth;

import java.util.Map;

public class KeycloakUser {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String username;
    public String enabled;
    public Map attributes;
    // public Map<String,String[]> clientRoles;
    public String[] realmRoles;

    @Override
    public String toString() {
        return "KeycloakUser{" + "lastName=" + lastName + ", email=" + email + ", username=" + username + ", enabled="
                + enabled + ", attributes=" + attributes + ", realmRoles=" + realmRoles + '}';
    }

}
