package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.subjects;
import com.project.EstudanteMais.Entity.teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface assessmentRepository extends JpaRepository<assessment, UUID> {

  assessment findByassessmentID(UUID id);

  List<assessment> findByteacher(teacher teacher);

  List<assessment> findByclasses(classes classes);

  List<assessment> findByclassesAndSubjects(classes classes, subjects subjects);

  @Modifying
  @Transactional
  @Query(value = "UPDATE assessment SET assessment_name = ?1, assessment_date = ?2," +
          "classes_classid = ?3, subjects_subjectid = ?4 WHERE assessmentid = ?5",nativeQuery = true)
  void updateAssessment(String name,String date,UUID classID, UUID subjectID, UUID id);
}
