package eu.palantir.portal.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;

// TO BE ADDED FOR COMPLETE AUTH
// import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
// import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
// import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

// TO BE ADDED FOR COMPLETE AUTH
// @SecuritySchemes(value = {
//         @SecurityScheme(securitySchemeName = "apiKey", type = SecuritySchemeType.HTTP, scheme = "Bearer") })
@Singleton
public class ApiResource {

    private final AuthResource authResource;
    private final ProfileResource profileResource;
    private final ServiceResource serviceResource;
    private final EventsResource eventsResource;

    @Inject
    public ApiResource(AuthResource authResource, ProfileResource profileResource, ServiceResource serviceResource,
            EventsResource eventsResource) {
        this.authResource = authResource;
        this.profileResource = profileResource;
        this.serviceResource = serviceResource;
        this.eventsResource = eventsResource;
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

    @Path("/events")
    public EventsResource getEventsResource() {
        return this.eventsResource;
    }

}
