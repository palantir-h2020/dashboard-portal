package eu.palantir.portal.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class NotFoundAlertException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public NotFoundAlertException(String entityName) {
        super(Response.status(NOT_FOUND).entity("Entity " + entityName + "was not found")
                .header("message", "error." + "notfound").header("params", entityName).build());
    }
}
