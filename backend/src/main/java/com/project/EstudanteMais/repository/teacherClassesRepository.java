package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.TeacherClasses;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.subjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface teacherClassesRepository extends JpaRepository<TeacherClasses, UUID> {
  TeacherClasses findBytcID(UUID id);

  TeacherClasses findByClassesAndSubjects(classes classes, subjects subject);
}
