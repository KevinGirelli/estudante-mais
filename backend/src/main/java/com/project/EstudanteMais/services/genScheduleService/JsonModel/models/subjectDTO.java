package com.project.EstudanteMais.services.genScheduleService.JsonModel.models;

public record subjectDTO(
        String subject,
        String teacherName,
        int numberOfClasses,
        String teacherWorkingDays
) { }
