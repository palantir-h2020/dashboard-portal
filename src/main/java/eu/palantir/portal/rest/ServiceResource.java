package eu.palantir.portal.rest;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.logging.Logger;

@Singleton
public class ServiceResource {

    private static final Logger logger = Logger.getLogger(ServiceResource.class.getName());

    @GET
    @Path("/dummy")
    public Response dummy() {
        return Response.noContent().build();
    }

    @GET
    @Path("/date")
    public LocalDate dummyDate() {
        return LocalDate.now();
    }

    @GET
    @Path("/date-query")
    public String dummyDateQueryParam(@QueryParam("date") LocalDate date) {
        logger.info("Day of the month " + date.getDayOfMonth());
        return "hello#" + date;
    }

    @GET
    @Path("/date/{date}")
    public String dummyDatePathParam(@PathParam("date") LocalDate date) {
        logger.info("Day of the month " + date.getDayOfMonth());
        return "hello#" + date;
    }

}
