package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.TeacherClasses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface teacherClassesRepository extends JpaRepository<TeacherClasses, UUID> {
}
