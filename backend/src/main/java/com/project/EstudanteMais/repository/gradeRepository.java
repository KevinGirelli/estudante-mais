package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.grade;
import com.project.EstudanteMais.Entity.student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface gradeRepository extends JpaRepository<grade, UUID> {

  List<grade> findByStudentAndQuarterAndAssessment(student student, int quarter, assessment asses);

  grade findByAssessmentAndStudent(assessment asses, student student);

  List<grade> findBystudent(student student);

  @Modifying
  @Transactional
  @Query(value = "UPDATE grade SET grade_value = ?1 WHERE student_studentid = ?2",nativeQuery = true)
  void updateStudentGradeValue(float gradeValue, UUID studentID);
}
