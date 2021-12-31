package eu.ubitech.palantir.portal.rest;

import org.eclipse.microprofile.jwt.JsonWebToken;

import eu.ubitech.palantir.portal.dto.PersonDto;
import eu.ubitech.palantir.portal.dto.options.extensions.ToOptionsPerson;
import eu.ubitech.palantir.portal.service.PersonService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Singleton
@RolesAllowed("trainer")
public class PersonResource {

  @Inject
  PersonService personService;

  @Inject
  JsonWebToken jwt;

  @Transactional
  @GET
  public Response getPersons(@BeanParam ToOptionsPerson options) {
    return Response.ok(personService.getPersons(options)).build();
  }

  @Transactional
  @GET
  @Path("/elastic")
  public Response getPersonsFromElastic(@BeanParam ToOptionsPerson options) {
    return Response.ok(personService.getPersonsFromElastic(options)).build();
  }

  @Transactional
  @GET
  @Path("/{id}")
  public Response getPerson(@PathParam("id") Long id) {
    return Response.ok(personService.getPerson(id)).build();
  }

  @POST
  @Transactional
  public Response persistOrUpdatePerson(@Valid PersonDto personDto) {
    return Response.ok(personService.persistOrUpdatePerson(personDto)).build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response deletePerson(@PathParam("id") Long id) {
    personService.deletePerson(id);
    return Response.ok().build();
  }

  @DELETE
  @Path("/elastic/{id}")
  @Transactional
  public Response deletePersonElastic(@PathParam("id") Long id) {
    return this.deletePerson(id);
  }

  @Transactional
  @Path("/search")
  @GET
  public Response getPersons(@QueryParam("lastname") String lastName) {
    return Response.ok(personService.searchPersons(lastName)).build();
  }

  @GET
  @Path("/genders")
  public Response getGenders() {
    return Response.ok(personService.getGenders()).build();
  }

}
