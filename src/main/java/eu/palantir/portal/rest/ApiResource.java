package eu.palantir.portal.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;

@Singleton
public class ApiResource {

    private final AuthResource authResource;
    private final ProfileResource profileResource;
    private final EventsResource eventsResource;

    @Inject
    public ApiResource(AuthResource authResource, ProfileResource profileResource, EventsResource eventsResource) {
        this.authResource = authResource;
        this.profileResource = profileResource;

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

    @Path("/events")
    public EventsResource getEventsResource() {
        return this.eventsResource;
    }

}
