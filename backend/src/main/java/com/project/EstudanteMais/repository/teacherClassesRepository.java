package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.TeacherClasses;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.subjects;
import com.project.EstudanteMais.Entity.teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface teacherClassesRepository extends JpaRepository<TeacherClasses, UUID> {
  TeacherClasses findBytcID(UUID id);

  TeacherClasses findByClassesAndSubjects(classes classes, subjects subject);

  List<TeacherClasses> findByteacher(teacher teacher);

  List<TeacherClasses> findByclasses(classes classes);

  TeacherClasses findByClassesAndSubjectsAndTeacher(classes classes, subjects subjects, teacher teacher);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM teacher_classes WHERE classes_classid = ?1 AND teacher_teacherid = ?2 AND subjects_subjectid = ?3",
  nativeQuery = true)
  void deleteTeacherFromClass(UUID classID, UUID teacherID, UUID subjectID);
}
