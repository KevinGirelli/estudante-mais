package com.project.EstudanteMais.Entity.dto;

import java.util.Date;
import java.util.UUID;

public class studentDataDTO {

  private UUID studentID;
  private String student_fullname;
  private String student_email;
  private String studentcpf;
  private String student_age;

  public UUID getStudentID() {
    return studentID;
  }

  public void setStudentID(UUID studentID) {
    this.studentID = studentID;
  }

  public String getStudent_fullname() {
    return student_fullname;
  }

  public void setStudent_fullname(String student_fullname) {
    this.student_fullname = student_fullname;
  }

  public String getStudent_email() {
    return student_email;
  }

  public void setStudent_email(String student_email) {
    this.student_email = student_email;
  }

  public String getStudentcpf() {
    return studentcpf;
  }

  public void setStudentcpf(String studentcpf) {
    this.studentcpf = studentcpf;
  }

  public String getStudent_age() {
    return student_age;
  }

  public void setStudent_age(String student_age) {
    this.student_age = student_age;
  }

  public studentDataDTO(UUID studentID, String student_fullname, String student_email, String studentcpf, String student_age) {
    this.studentID = studentID;
    this.student_fullname = student_fullname;
    this.student_email = student_email;
    this.studentcpf = studentcpf;
    this.student_age = student_age;
  }
}
