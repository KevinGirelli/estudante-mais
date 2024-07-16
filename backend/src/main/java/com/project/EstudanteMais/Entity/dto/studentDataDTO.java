package com.project.EstudanteMais.Entity.dto;

import java.util.Date;

public class studentDataDTO {
  private String student_fullname;
  private String student_email;
  private String studentcpf;
  private Date student_age;

  public studentDataDTO(String student_fullname, String student_email, String studentcpf, Date student_age) {
    this.student_fullname = student_fullname;
    this.student_email = student_email;
    this.studentcpf = studentcpf;
    this.student_age = student_age;
  }
}
