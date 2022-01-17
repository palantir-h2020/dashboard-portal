package eu.palantir.portal.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
public class Token extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String uuid;

    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    private LocalDateTime expirationTimestamp;

    public Token() {
    }

    public Token(User user, Type type) {
        this.user = user;
        this.type = type;
        this.uuid = UUID.randomUUID().toString();
        this.createdTimestamp = LocalDateTime.now(ZoneOffset.UTC);
        this.expirationTimestamp = this.createdTimestamp.plus(1, ChronoUnit.DAYS);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(LocalDateTime expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public boolean hasExpired() {
        return LocalDateTime.now(ZoneOffset.UTC).isAfter(this.expirationTimestamp);
    }

    public enum Type {

        RESET_PASSWORD("Reset Password"),
        VERIFY_EMAIL("Verify Email");

        private final String description;

        private Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

    }

}
