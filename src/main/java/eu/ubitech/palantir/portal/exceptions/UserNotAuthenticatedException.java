package eu.ubitech.palantir.portal.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

public class UserNotAuthenticatedException extends WebApplicationException {
  private static final long serialVersionUID = 1L;

  public UserNotAuthenticatedException() {
    this("Authentication is required");
  }

  public UserNotAuthenticatedException(String message) {
    super(Response.status(UNAUTHORIZED).entity(message).build());
  }
}
