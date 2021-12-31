package eu.ubitech.palantir.portal.rest;

import org.eclipse.microprofile.jwt.JsonWebToken;

import eu.ubitech.palantir.portal.dto.UserDto;
import eu.ubitech.palantir.portal.model.User;
import eu.ubitech.palantir.portal.service.ProfileService;
import eu.ubitech.palantir.portal.util.JwtClaim;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
public class ProfileResource {

  @Inject
  JsonWebToken jwt;

  @Inject
  ProfileService profileService;

  @GET
  @Path("/")
  public Response getProfile() {
    return Response.ok(User.findById(Long.parseLong(jwt.getClaim(JwtClaim.USER_ID.toString()).toString()))).build();
  }

  @POST
  @Path("/avatar")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response setAvatar(UserDto userDto) {
    profileService.updateAvatar(Long.parseLong(jwt.getClaim(JwtClaim.USER_ID.toString()).toString()),
        userDto.getAvatar());
    return Response.ok().build();
  }

  @POST
  @Path("/timezone")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response setTimezone(UserDto userDto) {
    profileService.updateTimezone(Long.parseLong(jwt.getClaim(JwtClaim.USER_ID.toString()).toString()),
        userDto.getTimezone());
    return Response.ok().build();
  }

  @POST
  @Path("/date-format")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response setDateFormat(UserDto userDto) {
    profileService.updateDateFormat(Long.parseLong(jwt.getClaim(JwtClaim.USER_ID.toString()).toString()),
        userDto.getDateFormat());
    return Response.ok().build();
  }

}
