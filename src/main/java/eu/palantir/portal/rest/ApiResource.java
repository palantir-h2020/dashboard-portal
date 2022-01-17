package eu.palantir.portal.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;

@Singleton
public class ApiResource {

    private final AuthResource authResource;
    private final ProfileResource profileResource;
    private final AlertResource alertResource;

    @Inject
    public ApiResource(AuthResource authResource, ProfileResource profileResource, AlertResource alertResource) {
        this.authResource = authResource;
        this.profileResource = profileResource;

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

    @Path("/alert")
    public AlertResource getAlertResource() {
        return alertResource;
    }

}
