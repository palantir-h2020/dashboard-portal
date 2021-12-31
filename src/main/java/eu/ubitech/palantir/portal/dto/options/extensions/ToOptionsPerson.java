package eu.ubitech.palantir.portal.dto.options.extensions;

import javax.ws.rs.QueryParam;

import eu.ubitech.palantir.portal.dto.options.ToList;
import eu.ubitech.palantir.portal.dto.options.ToOptions;

import java.time.LocalDate;

public class ToOptionsPerson extends ToOptions {

  @QueryParam("lastName")
  public String lastName;

  @QueryParam("gender")
  public ToList gender;

  @QueryParam("birthDateFrom")
  public LocalDate birthDateFrom;

  @QueryParam("birthDateTo")
  public LocalDate birthDateTo;

}
