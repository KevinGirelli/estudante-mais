package com.project.EstudanteMais.Entity.dto;

import java.util.List;

public record updateTeacherDataDTO(String teacherID,String nome, String email, String cpf, List<String> subjects, List<String> teacherClasses) {
}
