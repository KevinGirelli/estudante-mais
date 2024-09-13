package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.attendence;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.Entity.subjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface attendenceRepository extends JpaRepository<attendence, UUID> {
  List<attendence> findBysubjectsAndQuarterAndStudent(subjects subject, int quarter, student student);

  List<attendence> findBystudent(student student);
}
