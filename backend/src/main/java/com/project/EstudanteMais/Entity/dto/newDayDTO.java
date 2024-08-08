package com.project.EstudanteMais.Entity.dto;

public record newDayDTO(
        String diaryID,
        String subjectID,

        String subjectName,
        String dayContent,
        String observations,
        String registryDate,
        String classID,

        String className,
        String teacherID
) {
}
