package com.project.EstudanteMais.services.genScheduleService.JsonModel.models;

public record settingsDTO(
        int maxConsecutiveClasses,
        int maxClassesPerWeek,
        int maxClassPerDay
) {
}
