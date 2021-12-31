package eu.ubitech.palantir.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ObjectPath;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.PropertyValue;

@Indexed
@Entity
public class Person extends PanacheEntityBase {

  @Id
  @GeneratedValue
  @GenericField(name = "id", sortable = Sortable.YES, aggregable = Aggregable.YES, projectable = Projectable.YES)
  private Long id;

  @NotNull
  @GenericField(name = "lastName", sortable = Sortable.YES, aggregable = Aggregable.YES, projectable = Projectable.YES)
  private String lastName;

  @NotNull
  @GenericField(name = "firstName", sortable = Sortable.YES, aggregable = Aggregable.YES, projectable = Projectable.YES)
  private String firstName;

  @Enumerated(EnumType.STRING)
  @GenericField(name = "gender", sortable = Sortable.YES, aggregable = Aggregable.YES, projectable = Projectable.YES)
  private Gender gender;

  @GenericField(name = "birthDate", sortable = Sortable.YES, aggregable = Aggregable.YES, projectable = Projectable.YES)
  private LocalDate birthDate;

  @Lob
  @Type(type = "org.hibernate.type.TextType")
  @FullTextField(analyzer = "greek", name = "description", projectable = Projectable.YES)
  private String description;

  @CreationTimestamp
  @GenericField(name = "createdtimestamp", sortable = Sortable.YES, aggregable = Aggregable.NO, projectable = Projectable.YES)
  private Instant createdTimestamp;

  @UpdateTimestamp
  @GenericField(name = "updatedtimestamp", sortable = Sortable.YES, aggregable = Aggregable.NO, projectable = Projectable.YES)
  private Instant updatedTimestamp;

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

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
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

  public void setDescription(String comments) {
    this.description = comments;
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
