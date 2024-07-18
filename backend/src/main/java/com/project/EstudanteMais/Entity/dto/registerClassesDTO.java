package com.project.EstudanteMais.Entity.dto;

import java.util.List;

public record registerClassesDTO(String className, String gradeType, int gradeNumber, List<String> subjects) {
}
