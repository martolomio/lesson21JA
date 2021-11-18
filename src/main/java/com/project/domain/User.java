package com.project.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "user")
public class User {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private boolean active;
    @ElementCollection(targetClass = Access.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "access", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Access> access;

    public User() {
    }

    public User(String firstName, boolean active, String lastName, String email, String password, Set<Access> access) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.access = access;
        this.active = active;
    }

    public User(Integer id, boolean active, String firstName, String lastName, String email, String password, Set<Access> access) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.access = access;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Access> getAccess() {
        return access;
    }

    public void setAccess(Set<Access> access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return active == user.active && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(access, user.access);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, active, access);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", access=" + access +
                '}';
    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        return getAccessLevels();
//    }
//
//
//    @Override
//    public String getUsername() {
//
//        return email;
//    }
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//
//        return true;
//    }
//
//
//    @Override
//    public boolean isAccountNonLocked() {
//
//        return true;
//    }
//
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//
//        return true;
//    }
//
//
//    @Override
//    public boolean isEnabled() {
//
//        return isActive();
//    }
}