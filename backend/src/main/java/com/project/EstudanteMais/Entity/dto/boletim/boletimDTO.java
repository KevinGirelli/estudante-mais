package com.project.EstudanteMais.Entity.dto.boletim;

import java.util.List;

public record boletimDTO(
        String studentName,
        String shift,
        String className,
        String stage,
        String year,
        List<subjectBoletim> subjectsGrades,
        int amountOfClasses
) {
}
