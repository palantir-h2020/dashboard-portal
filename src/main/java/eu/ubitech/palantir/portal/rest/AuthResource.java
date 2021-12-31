package eu.ubitech.palantir.portal.rest;

import org.eclipse.microprofile.jwt.JsonWebToken;

import eu.ubitech.palantir.portal.dto.Credentials;
import eu.ubitech.palantir.portal.dto.Token;
import eu.ubitech.palantir.portal.exceptions.EmailAlreadyExistsException;
import eu.ubitech.palantir.portal.exceptions.InternalServerErrorException;
import eu.ubitech.palantir.portal.exceptions.UsernameAlreadyExistsException;
import eu.ubitech.palantir.portal.model.User;
import eu.ubitech.palantir.portal.service.auth.AuthService;
import eu.ubitech.palantir.portal.service.auth.KeycloakUser;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

@Singleton
public class AuthResource {

  private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

  @Inject
  JsonWebToken jwt;

  @Inject
  EntityManager em;

  @Inject
  AuthService authservice;

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response login(Credentials credentials) {
    try {
      return Response.ok(authservice.login(credentials.username, credentials.password)).build();
    } catch (IOException ex) {
      ex.printStackTrace();
      throw new InternalServerErrorException();
    }
  }

  @POST
  @Path("/refresh")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response refresh(String refreshToken) {
    Token token = authservice.refresh(refreshToken);
    if (token == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    return Response.ok(token).build();
  }

  @POST
  @Path("/add-user")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response adduser(User user) throws Exception {
    try {
      User tempUser = User.find("username = ?1", user.getUsername()).firstResult();
      if (tempUser != null) {
        throw new UsernameAlreadyExistsException();
      }
      tempUser = User.find("email = ?1", user.getEmail()).firstResult();
      if (tempUser != null) {
        throw new EmailAlreadyExistsException();
      }
      user.persist();
      KeycloakUser keycloakUser = new KeycloakUser();
      keycloakUser.username = user.getUsername();
      keycloakUser.email = user.getEmail();
      keycloakUser.realmRoles = user.realmRoles;
      logger.info(keycloakUser.realmRoles[0]);
      if (keycloakUser.realmRoles[0].equals("trainee")) {
        keycloakUser.enabled = "true";
      }
      keycloakUser.attributes = new HashMap<>();
      keycloakUser.attributes.put("userid", user.getId().toString());
      keycloakUser.attributes.put("userfullname", user.getUsername());
      KeycloakUser registereduser = authservice.addUser(keycloakUser, user.password);
      return Response.ok(registereduser).build();
    } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException ex) {
      em.clear();
      return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
    }
  }

}
