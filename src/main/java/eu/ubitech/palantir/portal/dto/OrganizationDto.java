package eu.ubitech.palantir.portal.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class OrganizationDto {

  private Long id;

  @NotNull
  @NotBlank
  private String name;

  private Instant createdTimestamp;

  private Instant updatedTimestamp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Instant getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(Instant createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public Instant getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  public void setUpdatedTimestamp(Instant updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
  }
}
