package eu.ubitech.palantir.portal.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.Instant;

@Entity
@Table(name = "userp")
public class User extends PanacheEntityBase {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  @CreationTimestamp
  private Instant createdTimestamp;

  @Transient
  public String[] realmRoles;
  @Transient
  public String password;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Instant getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(Instant createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public User() {
  }

  public User(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public User(Long id, String username, Instant createdTimestamp) {
    this.id = id;
    this.username = username;
    this.createdTimestamp = createdTimestamp;
  }

}
