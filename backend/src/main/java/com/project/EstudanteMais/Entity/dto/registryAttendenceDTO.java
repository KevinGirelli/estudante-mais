package com.project.EstudanteMais.Entity.dto;

import java.util.List;

public record registryAttendenceDTO(String classID, String teacherID, int quantity
, String date, List<String> students, String subjectID) {
}
