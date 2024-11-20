package com.project.EstudanteMais.Entity.dto;

import com.project.EstudanteMais.Entity.periodType;
import com.project.EstudanteMais.Entity.teacher;

import java.util.UUID;

public record classesDTO(String classID, String className, int gradeNumber, String gradeType, String teacher, periodType type) {
}
