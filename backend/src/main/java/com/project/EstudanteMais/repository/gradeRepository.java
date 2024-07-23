package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.assessment;
import com.project.EstudanteMais.Entity.grade;
import com.project.EstudanteMais.Entity.student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface gradeRepository extends JpaRepository<grade, UUID> {

  List<grade> findByStudentAndQuarterAndAssessment(student student, int quarter, assessment asses);
}
