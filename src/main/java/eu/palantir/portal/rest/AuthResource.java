package eu.palantir.portal.rest;

import eu.palantir.portal.dto.AuthToken;
import eu.palantir.portal.dto.Credentials;
import eu.palantir.portal.dto.ResetPasswordDto;
import eu.palantir.portal.dto.SignUpDto;
import eu.palantir.portal.exceptions.EmailAlreadyExistsException;
import eu.palantir.portal.exceptions.UsernameAlreadyExistsException;
import eu.palantir.portal.service.auth.AuthService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Singleton
public class AuthResource {

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(Credentials credentials) {
        return Response.ok(authService.login(credentials.getUsername(), credentials.getPassword())).build();
    }

    @POST
    @Path("/refresh")
    public Response refresh(String refreshToken) {
        AuthToken authToken = authService.refresh(refreshToken);
        if (authToken == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(authToken).build();
    }

    @POST
    @Path("/sign-up")
    public Response signUp(SignUpDto signUpDto) {
        try {
            authService.addUser(signUpDto);
        } catch (UsernameAlreadyExistsException e) {
            return Response.status(Status.CONFLICT).entity("Username already exists!").build();
        } catch (EmailAlreadyExistsException e) {
            return Response.status(Status.CONFLICT).entity("E-mail already exists!").build();
        }
        authService.sendVerifyEmail(signUpDto.getUsername());
        return Response.noContent().build();
    }

    @POST
    @Path("/forgot-password")
    public Response forgotPassword(@QueryParam("username") String username) {
        authService.sendResetPasswordEmail(username);
        return Response.noContent().build();
    }

    @POST
    @Path("/reset-password")
    public Response resetPassword(@QueryParam("uuid") String uuid, ResetPasswordDto resetPasswordDto) {
        authService.resetPasswordFromEmail(resetPasswordDto, uuid);
        return Response.noContent().build();
    }

    @POST
    @Path("/verify-email")
    public Response verifyEmail(@QueryParam("uuid") String uuid) {
        authService.verifyEmail(uuid);
        return Response.noContent().build();
    }

}
