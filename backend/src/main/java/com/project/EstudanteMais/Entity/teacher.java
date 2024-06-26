package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class teacher implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID teacherID;

    @Column(name = "teacherEmail", nullable = false)
    private String teacherEmail;

    @Column(name = "teacherPassword", nullable = false)
    private String teacherPassword;

    @Column(name = "teacherName", nullable = false)
    private String teacherName;

    @Column(name = "teacherSubject", nullable = false)
    private String teacherSubject;

    @Column(name = "role")
    private UserRoles role;

    public teacher(){
        super();
    }
    public teacher(String teacherEmail, String teacherPassword, String teacherName, String teacherSubject, UserRoles role){
        this.teacherEmail = teacherEmail;
        this.teacherPassword = teacherPassword;
        this.teacherName = teacherName;
        this.teacherSubject = teacherSubject;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("TEACHER"));
    }

    @Override
    public String getPassword() {
        return teacherPassword;
    }

    @Override
    public String getUsername() {
        return this.teacherEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
