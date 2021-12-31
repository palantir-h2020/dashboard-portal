package eu.ubitech.palantir.portal.exceptions;

public class EmailAlreadyUsedException extends BadRequestAlertException {
  public EmailAlreadyUsedException() {
    super("Email is already in use!", "userManagement", "emailexists");
  }
}
