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
import java.util.logging.Logger;

@Singleton
public class AuthResource {

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    AuthService authservice;

    @POST
    @Path("/login")
    public Response login(Credentials credentials) {
        return Response.ok(authservice.login(credentials.getUsername(), credentials.getPassword())).build();
    }

    @POST
    @Path("/refresh")
    public Response refresh(String refreshToken) {
        AuthToken authToken = authservice.refresh(refreshToken);
        if (authToken == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(authToken).build();
    }

    @POST
    @Path("/sign-up")
    public Response signUp(SignUpDto signUpDto) {
        try {
            authservice.addUser(signUpDto);
        } catch (UsernameAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), "Username Already Exists!").build();
        } catch (EmailAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), "Email Already Exists!").build();
        }
        authservice.sendVerifyEmail(signUpDto.getUsername());
        return Response.noContent().build();
    }

    @POST
    @Path("/forgot-password")
    public Response forgotPassword(@QueryParam("username") String username) {
        authservice.sendResetPasswordEmail(username);
        return Response.noContent().build();
    }

    @POST
    @Path("/reset-password")
    public Response resetPassword(@QueryParam("uuid") String uuid, ResetPasswordDto resetPasswordDto) {
        authservice.resetPasswordFromEmail(resetPasswordDto, uuid);
        return Response.noContent().build();
    }

    @POST
    @Path("/verify-email")
    public Response verifyEmail(@QueryParam("uuid") String uuid) {
        authservice.verifyEmail(uuid);
        return Response.noContent().build();
    }

}
