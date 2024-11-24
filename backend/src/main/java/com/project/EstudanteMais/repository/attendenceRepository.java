package com.project.EstudanteMais.repository;

import com.project.EstudanteMais.Entity.attendence;
import com.project.EstudanteMais.Entity.classes;
import com.project.EstudanteMais.Entity.student;
import com.project.EstudanteMais.Entity.subjects;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface attendenceRepository extends JpaRepository<attendence, UUID> {
  List<attendence> findBysubjectsAndQuarterAndStudent(subjects subject, int quarter, student student);

  List<attendence> findBystudent(student student);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM attendence WHERE attendenceid = ?1", nativeQuery = true)
  void deleteAttendence(UUID attendenceid);
}
