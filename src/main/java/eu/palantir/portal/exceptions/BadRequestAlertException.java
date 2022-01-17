package eu.palantir.portal.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class BadRequestAlertException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public BadRequestAlertException(String message, String entityName, String errorKey) {
        super(Response.status(BAD_REQUEST).entity(message).header("message", "error." + errorKey)
                .header("params", entityName).build());
    }
}
