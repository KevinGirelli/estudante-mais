package com.project.EstudanteMais.Entity.dto;

import java.sql.Date;

public record updateStudentDataDTO(String studentID, String fullname, String email, String cpf, Date age, String classID) {
}
