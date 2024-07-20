package com.project.EstudanteMais.Entity.dto;

import java.sql.Date;

public record createAssessmentDTO(String name, Date data, String classID, String teacherID) {
}
