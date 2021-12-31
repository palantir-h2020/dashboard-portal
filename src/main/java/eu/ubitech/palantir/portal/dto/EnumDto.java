package eu.ubitech.palantir.portal.dto;

import java.util.UUID;

public class EnumDto {

  private String id;

  private String description;

  public EnumDto(Long id, String description) {
    this.id = id.toString();
    this.description = description;
  }

  public EnumDto(UUID id, String description) {
    this.id = id.toString();
    this.description = description;
  }

  public EnumDto(String id, String description) {
    this.id = id;
    this.description = description;
  }

  public EnumDto() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
