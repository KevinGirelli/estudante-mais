package com.project.EstudanteMais.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class student implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID studentID;

  public UUID getStudentID() {
    return studentID;
  }

  public void setStudentID(UUID studentID) {
    this.studentID = studentID;
  }

  public String getStudentEmail() {
    return studentEmail;
  }

  public void setStudentEmail(String studentEmail) {
    this.studentEmail = studentEmail;
  }

  public String getStudentPassword() {
    return studentPassword;
  }

  public void setStudentPassword(String studentPassword) {
    this.studentPassword = studentPassword;
  }

  public UserRoles getRole() {
    return role;
  }

  public void setRole(UserRoles role) {
    this.role = role;
  }

  public String getRegistration() {
    return studentRegistration;
  }

  public void setRegistration(String registration) {
    this.studentRegistration = registration;
  }

  public String getStudentFullname() {
    return studentFullname;
  }

  public void setStudentFullname(String studentFullname) {
    this.studentFullname = studentFullname;
  }

  public String getStudentCPF() {
    return studentCPF;
  }

  public void setStudentCPF(String studentCPF) {
    this.studentCPF = studentCPF;
  }

  public String getStudentAge() {
    return studentAge;
  }

  public void setStudentAge(String studentAge) {
    this.studentAge = studentAge;
  }

  @Column(name = "studentEmail", nullable = false)
  private String studentEmail;

  @Column(name = "studentPassword",nullable = false)
      private String studentPassword;

  @Column(name = "role",nullable = false)
  private UserRoles role;

  @Column(name = "studentRegistration", nullable = false,unique = true)
  private String studentRegistration;

  @Column(name = "studentFullname",nullable = false)
  private String studentFullname;

  @Column(name = "studentCPF",nullable = false)
  private String studentCPF;

  @Column(name = "studentAge",nullable = false)
  private String studentAge;

  @ManyToOne
  classes classes;


  public student(){
    super();
  }
  public student(String studentEmail
  , String studentPassword,String studentFullname
  ,String studentCPF, String studentAge, UserRoles role, classes classes){
    this.studentEmail = studentEmail;
    this.studentPassword = studentPassword;
    this.studentFullname = studentFullname;
    this.studentCPF = studentCPF;
    this.studentAge = studentAge;
    this.classes = classes;
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
