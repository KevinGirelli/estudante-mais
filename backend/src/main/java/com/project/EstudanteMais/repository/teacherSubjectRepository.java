package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.subjects;
import com.project.EstudanteMais.Entity.teacher;
import com.project.EstudanteMais.Entity.teacherSubject;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface teacherSubjectRepository extends JpaRepository<teacherSubject, UUID> {
    List<teacherSubject> findByteacher(teacher teacher);

   teacherSubject findByTeacherAndSubject(teacher teacher, subjects subjects);

   List<teacherSubject> findBySubject(subjects subjects);

    @Transactional
   @Modifying
   @Query(value = "DELETE FROM teacher_subject WHERE teacher_teacherid = ?1", nativeQuery = true)
   void deleteTeacher(UUID teacherID);
}
