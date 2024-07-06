package com.project.EstudanteMais.Entity.dto;

import java.util.List;

public record registerTeacherDTO(String teacherEmail, String teacherPassword
, String teacherName, String teacherSubject, String teacherCPF, List<String> turmas) { }
