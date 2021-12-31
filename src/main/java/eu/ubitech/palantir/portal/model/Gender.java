package eu.ubitech.palantir.portal.model;

public enum Gender {

  MALE("Male"), FEMALE("Female");

  private final String description;

  private Gender(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }

}
