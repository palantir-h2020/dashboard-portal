package eu.ubitech.palantir.portal.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ElasticException extends WebApplicationException {
  private static final long serialVersionUID = 1L;

  public ElasticException() {
    this("Elastic Exception");
  }

  public ElasticException(String message) {
    super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build());
  }
}
