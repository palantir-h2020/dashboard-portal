package eu.palantir.portal.exceptions;

public class LoginAlreadyUsedException extends BadRequestAlertException {
    public LoginAlreadyUsedException() {
        super("Login name already used!", "userManagement", "userexists");
    }
}
