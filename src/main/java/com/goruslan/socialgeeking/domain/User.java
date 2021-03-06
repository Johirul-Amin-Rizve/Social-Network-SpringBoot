package com.goruslan.socialgeeking.domain;

import com.goruslan.socialgeeking.domain.validator.PasswordsMatch;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@PasswordsMatch
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotEmpty(message = "Email is required.")
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @NotEmpty(message = "Password is required.")
    @Column(length = 150)
    private String password;

    @NonNull
    @Column(nullable = true)
    private boolean enabled;

    // Sets up 3rd table between roles and users. User might have one or few roles so we fetch all the roles.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"), // (user) ID -> user_id
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id") // (role) id -> role_id
    )
    private Set<Role> roles = new HashSet<>();

    @NonNull
    @NotEmpty(message = "Full Name is required.")
    private String fullname;

    @NonNull
    @NotEmpty(message = "Education is required.")
    private String education;

    @NonNull
    @NotEmpty(message = "Username is required.")
    @Column(nullable = false, unique = true)
    private String username;

    @Transient // This annotation is used to declare what instance variables cannot be persisted to database
    @NotEmpty(message = "Password Confirmation is required.")
    private String confirmPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
