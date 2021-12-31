package eu.ubitech.palantir.portal.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;

@Singleton
public class ApiResource {

  private final AuthResource authResource;
  private final ProfileResource profileResource;
  private final ServiceResource serviceResource;
  private final PersonResource personResource;
  private final AlertResource alertResource;

  @Inject
  public ApiResource(AuthResource authResource, ProfileResource profileResource, ServiceResource serviceResource,
      PersonResource personResource, AlertResource alertResource) {
    this.authResource = authResource;
    this.profileResource = profileResource;
    this.serviceResource = serviceResource;
    this.personResource = personResource;
    this.alertResource = alertResource;
  }

  @Path("/auth")
  public AuthResource getAuthResource() {
    return authResource;
  }

  @Path("/profile")
  public ProfileResource getProfileResource() {
    return profileResource;
  }

  @Path("/service")
  public ServiceResource getServiceResource() {
    return serviceResource;
  }

  @Path("/persons")
  public PersonResource getPersonResource() {
    return personResource;
  }

  @Path("/alert")
  public AlertResource getAlertResource() {
    return alertResource;
  }

}
