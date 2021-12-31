package eu.ubitech.palantir.portal.exceptions;

public class EmailAlreadyExistsException extends Exception {
  public EmailAlreadyExistsException() {
    super("Email already exists");
  }
}
