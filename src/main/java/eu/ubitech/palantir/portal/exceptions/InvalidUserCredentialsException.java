package eu.ubitech.palantir.portal.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InvalidUserCredentialsException extends WebApplicationException {
  private static final long serialVersionUID = 1L;

  public InvalidUserCredentialsException() {
    this("Invalid user credentials");
  }

  public InvalidUserCredentialsException(String message) {
    super(Response.status(Response.Status.BAD_REQUEST).entity(message).build());
  }
}
