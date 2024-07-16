package com.project.EstudanteMais.Entity.dto;

import java.util.Date;
import java.util.UUID;

public class studentDataDTO {

  private UUID studentID;
  private String student_fullname;
  private String student_email;
  private String studentcpf;
  private String student_age;

  public studentDataDTO(UUID studentID, String student_fullname, String student_email, String studentcpf, String student_age) {
    this.studentID = studentID;
    this.student_fullname = student_fullname;
    this.student_email = student_email;
    this.studentcpf = studentcpf;
    this.student_age = student_age;
  }
}
