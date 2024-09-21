package com.project.EstudanteMais.Entity.dto;

import java.sql.Date;

public record updateStudentDataDTO(String studentID, String fullname, String email, String cpf, String age, String classID) {
}
