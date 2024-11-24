package com.project.EstudanteMais.Entity.dto.boletim;

import java.util.List;

public record subjectBoletim(
        String subjectName,
        List<String> grades,
        List<String> absences,
        float finalAverage,
        int totalAbsences
) {
}
