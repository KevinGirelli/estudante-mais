package com.project.EstudanteMais.Entity.dto;

import java.util.List;

public record teachersDTO
        (String teacherID,
         String teacherName,
         String teacherEmail,
         String cpf,
         List<String> subjects) {
}
