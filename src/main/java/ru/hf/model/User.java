package ru.hf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_NAME"})})
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @JsonIgnore
    @Column(name = "ACCOUNT_EXPIRED")
    private boolean accountExpired;

    @JsonIgnore
    @Column(name = "ACCOUNT_LOCKED")
    private boolean accountLocked;

    @JsonIgnore
    @Column(name = "CREDENTIALS_EXPIRED")
    private boolean credentialsExpired;

    @JsonIgnore
    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_AUTHORITIES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "AUTHORITY_ID"))
    @OrderBy
    @JsonIgnore
    private Collection<Authority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }
}
