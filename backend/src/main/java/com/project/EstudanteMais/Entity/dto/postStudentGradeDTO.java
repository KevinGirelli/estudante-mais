package com.project.EstudanteMais.Entity.dto;

public record postStudentGradeDTO(
        float gradeValue,
        String studentID,
        String assessID,
        String date,
        int quarter
){ }
