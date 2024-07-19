package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.subjects;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.Entity.teacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface teacherSubjectRepository extends JpaRepository<teacherSubject, UUID> {
  List<teacherSubject> findByteacher(teacher teacher);

   teacherSubject findByTeacherAndSubject(teacher teacher, subjects subjects);
}
