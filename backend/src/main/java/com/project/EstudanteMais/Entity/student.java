package com.project.EstudanteMais.Entity;

import com.project.EstudanteMais.Entity.dto.studentDataDTO;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@SqlResultSetMapping(
        name = "StudentDataMapping",
        classes = @ConstructorResult(
                targetClass = studentDataDTO.class,
                columns = {
                        @ColumnResult(name = "studentid", type = UUID.class),
                        @ColumnResult(name = "student_fullname"),
                        @ColumnResult(name = "student_email"),
                        @ColumnResult(name = "studentcpf"),
                        @ColumnResult(name = "student_age")
                }
        )
)
@NamedNativeQuery(
        name = "Student.getAllStudentFromClass",
        query = "SELECT studentid, student_fullname, student_email, studentcpf, student_age FROM student WHERE classes_classid = ?1",
        resultSetMapping = "StudentDataMapping"
)
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

  public String getStudentRegistration() {
    return studentRegistration;
  }

  public void setStudentRegistration(String studentRegistration) {
    this.studentRegistration = studentRegistration;
  }

  public com.project.EstudanteMais.Entity.classes getClasses() {
    return classes;
  }

  public void setClasses(com.project.EstudanteMais.Entity.classes classes) {
    this.classes = classes;
  }

  public Boolean getTwostepverification() {
    return twostepverification;
  }

  public void setTwostepverification(Boolean twostepverification) {
    this.twostepverification = twostepverification;
  }

  @Column(name = "studentAge",nullable = false)
  private String studentAge;

  @ManyToOne
  classes classes;

  public String getTwoStepCode() {
    return twoStepCode;
  }

  public void setTwoStepCode(String twoStepCode) {
    this.twoStepCode = twoStepCode;
  }

  @Column(name = "twostepverification",nullable = false)
  Boolean twostepverification;

  @Column(name = "twoStepCode", nullable = true)
  String twoStepCode;


  public student(){
    super();
  }
  public student(String studentEmail
  , String studentPassword,String studentFullname
  ,String studentCPF, String studentAge, boolean twostepverification, UserRoles role, classes classes){
    this.studentEmail = studentEmail;
    this.studentPassword = studentPassword;
    this.studentFullname = studentFullname;
    this.studentCPF = studentCPF;
    this.studentAge = studentAge;
    this.twostepverification = twostepverification;
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
