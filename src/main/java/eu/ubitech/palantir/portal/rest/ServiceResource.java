package eu.ubitech.palantir.portal.rest;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import eu.ubitech.palantir.portal.service.PersonService;

import java.time.LocalDate;
import java.util.logging.Logger;

@Singleton
public class ServiceResource {

  private static final Logger logger = Logger.getLogger(ServiceResource.class.getName());

  @GET
  @Path("/dummy")
  @Transactional
  public Response dummy() {
    return Response.ok().build();
  }

  @GET
  @Path("/date")
  public LocalDate dummyDate() {
    return LocalDate.now();
  }

  @GET
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
