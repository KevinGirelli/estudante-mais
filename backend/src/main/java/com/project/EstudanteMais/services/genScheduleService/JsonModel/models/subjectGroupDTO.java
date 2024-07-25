package com.project.EstudanteMais.services.genScheduleService.JsonModel.models;

import java.util.List;

public record subjectGroupDTO(
        String groupName,
        List<subjectDTO> subjects
) {}
