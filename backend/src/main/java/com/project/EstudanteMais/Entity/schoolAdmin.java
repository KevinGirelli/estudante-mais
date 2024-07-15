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

  @Column(name = "twostepverificaiton",nullable = false)
  Boolean twostepverification;

  @Column(name = "twoStepCode", nullable = true)
  String twoStepCode;

  public String getTwoStepCode() {
    return twoStepCode;
  }

  public void setTwoStepCode(String twoStepCode) {
    this.twoStepCode = twoStepCode;
  }

  public UUID getAdminID() {
    return adminID;
  }

  public void setAdminID(UUID adminID) {
    this.adminID = adminID;
  }

  public String getDirectorEmail() {
    return directorEmail;
  }

  public void setDirectorEmail(String directorEmail) {
    this.directorEmail = directorEmail;
  }

  public String getLoginCode() {
    return loginCode;
  }

  public void setLoginCode(String loginCode) {
    this.loginCode = loginCode;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserRoles getRole() {
    return role;
  }

  public void setRole(UserRoles role) {
    this.role = role;
  }

  public Boolean getTwostepverification() {
    return twostepverification;
  }

  public void setTwostepverification(Boolean twostepverification) {
    this.twostepverification = twostepverification;
  }

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
