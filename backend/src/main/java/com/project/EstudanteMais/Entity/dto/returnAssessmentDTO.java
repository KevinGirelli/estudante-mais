package com.project.EstudanteMais.Entity.dto;

import java.sql.Date;

public record returnAssessmentDTO(String id, String name, String className, String subjectName, String date,
                                  String classID, String subjectID, String teacherName, String teacherID) {
}
