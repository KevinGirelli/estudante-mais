package com.project.EstudanteMais.Entity.dto;

import java.util.List;

public record registerTeacherDTO(String teacherEmail, String teacherPassword
, String teacherName, String teacherCPF,String phoneNumber,List<String> subjects) { }
