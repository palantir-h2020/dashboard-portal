package eu.palantir.portal.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class MinioException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public MinioException() {
        this("Minio Exception");
    }

    public MinioException(String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build());
    }
}
