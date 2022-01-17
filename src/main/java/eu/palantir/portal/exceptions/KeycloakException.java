package eu.palantir.portal.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class KeycloakException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public KeycloakException() {
        this("Keycloak Exception");
    }

    public KeycloakException(String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build());
    }
}
