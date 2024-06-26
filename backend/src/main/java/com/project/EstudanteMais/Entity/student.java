package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class student implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long studentID;

  @Column(name = "studentEmail", nullable = false)
  private String studentEmail;

  @Column(name = "studentPassword",nullable = false)
  private String studentPassword;

  @Column(name = "role",nullable = false)
  private UserRoles role;

  @Column(name = "studentFirstName",nullable = false)
  private String studentFirstName;

  @Column(name = "studentLastName",nullable = false)
  private String studentLastName;

  @Column(name = "studentCPF",nullable = false)
  private String studentCPF;

  @Column(name = "studentAge",nullable = false)
  private String studentAge;

  public student(){
    super();
  }
  public student(String studentEmail
  , String studentPassword,String studentFirstName,String studentLastName
  ,String studentCPF, String studentAge, UserRoles role){
    this.studentEmail = studentEmail;
    this.studentPassword = studentPassword;
    this.studentFirstName = studentFirstName;
    this.studentLastName = studentLastName;
    this.studentCPF = studentCPF;
    this.studentAge = studentAge;
    this.role = role;
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("STUDENT"));
  }

  @Override
  public String getPassword() {
    return studentPassword;
  }

  @Override
  public String getUsername() {
    return studentEmail;
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
