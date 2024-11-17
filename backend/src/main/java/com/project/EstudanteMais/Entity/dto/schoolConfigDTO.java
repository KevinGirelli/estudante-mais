package com.project.EstudanteMais.Entity.dto;

public record schoolConfigDTO(
        String biDate,
        String triDate,
        String semDate,
        int schoolPeriod,
        int maxConsecutiveClasses
) {
}
