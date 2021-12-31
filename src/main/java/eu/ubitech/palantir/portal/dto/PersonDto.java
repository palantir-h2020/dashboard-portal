package eu.ubitech.palantir.portal.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PersonDto {

  private Long id;

  @NotNull
  private String lastName;

  @NotNull
  private String firstName;

  private EnumDto gender;

  private LocalDate birthDate;

  private String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public EnumDto getGender() {
    return gender;
  }

  public void setGender(EnumDto gender) {
    this.gender = gender;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
