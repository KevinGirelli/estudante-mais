package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class schoolAdmin implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID adminID;

  @Column(name = "directorEmail")
  private String directorEmail;

  @Column(name = "loginCode")
  private String loginCode;

  @Column(name = "adminPassword")
  private String password;

  @Column(name = "role")
  private UserRoles role;

  @Column(name = "authenticationCode")
  private int adminCode;

  public schoolAdmin(){
    super();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ADMIN"),new SimpleGrantedAuthority("STUDENT"),new SimpleGrantedAuthority("TEACHER"));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return directorEmail;
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
